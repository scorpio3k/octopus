package org.scorpio.octopus.debug;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.ArrayReference;
import com.sun.jdi.BooleanValue;
import com.sun.jdi.Bootstrap;
import com.sun.jdi.ByteValue;
import com.sun.jdi.CharValue;
import com.sun.jdi.ClassType;
import com.sun.jdi.DoubleValue;
import com.sun.jdi.Field;
import com.sun.jdi.FloatValue;
import com.sun.jdi.IntegerValue;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.Location;
import com.sun.jdi.LongValue;
import com.sun.jdi.ObjectReference;
import com.sun.jdi.ReferenceType;
import com.sun.jdi.ShortValue;
import com.sun.jdi.StackFrame;
import com.sun.jdi.StringReference;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.VirtualMachine;
import com.sun.jdi.VirtualMachineManager;
import com.sun.jdi.connect.AttachingConnector;
import com.sun.jdi.connect.Connector;
import com.sun.jdi.event.BreakpointEvent;
import com.sun.jdi.event.Event;
import com.sun.jdi.event.EventIterator;
import com.sun.jdi.event.EventQueue;
import com.sun.jdi.event.EventSet;
import com.sun.jdi.event.VMDisconnectEvent;
import com.sun.jdi.request.BreakpointRequest;
import com.sun.jdi.request.EventRequest;
import com.sun.jdi.request.EventRequestManager;
import com.sun.tools.jdi.SocketAttachingConnector;

/**
 * Java 远程调试类
 */
public class DebugRemoteJava{

    /**
     * 远程虚拟机
     */
    private VirtualMachine vm;

    private DebugLogger logger = new DefaultDebugLogger();;

    /**
     * @param hostname
     * @param port
     */
    public DebugRemoteJava(String hostname, Integer port){
        try {
            vm = connectVirtualMachine(hostname, port);
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void setLogger(DebugLogger logger) {
        this.logger = logger;
    }


    /**
     * 连接远程JVM
     * @param hostname
     * @param port
     * @return
     * @throws Exception
     */
    private VirtualMachine connectVirtualMachine(String hostname, Integer port) throws Exception{
        VirtualMachineManager vmm = Bootstrap.virtualMachineManager();
        List<AttachingConnector> connectors = vmm.attachingConnectors();
        SocketAttachingConnector sac = null;
        for(AttachingConnector ac : connectors){
            if(ac instanceof SocketAttachingConnector){
                sac = (SocketAttachingConnector) ac;
            }
        }

        if (sac == null) throw new Exception("未找到可用的SocketAttachingConnector连接器");
        Map<String, Connector.Argument> arguments = sac.defaultArguments();
        arguments.get("hostname").setValue(hostname);
        arguments.get("port").setValue(String.valueOf(port));
        return sac.attach(arguments);
    }

    /**
     * 标记断点
     * @param clazz
     * @param line
     * @throws Exception
     */
    public void markBreakpoint(Class clazz, Integer line) throws Exception{
        EventRequestManager eventRequestManager = vm.eventRequestManager();

        List<ReferenceType> rt = vm.classesByName(clazz.getName());
        if(rt == null || rt.isEmpty()) throw new Exception("无法获取有效的debug类");
        ClassType classType = (ClassType) rt.get(0);

        List<Location> locations = classType.locationsOfLine(line);
        if(locations == null || locations.isEmpty()) throw new Exception("无法获取有效的debug行");
        Location location = locations.get(0);

        BreakpointRequest breakpointRequest = eventRequestManager.createBreakpointRequest(location);
        breakpointRequest.setSuspendPolicy(EventRequest.SUSPEND_EVENT_THREAD);
        breakpointRequest.enable();

    }

    /**
     * 跟踪断点信息
     * @throws Exception
     */
    public void traceBreakpoint() throws Exception{

        EventQueue eventQueue = vm.eventQueue();
        try {
            boolean runable = true;
            while (runable) {
                EventSet eventSet = eventQueue.remove();
                EventIterator eventIterator = eventSet.eventIterator();
                while (eventIterator.hasNext()) {
                    Event event = eventIterator.next();
                    if (event instanceof BreakpointEvent)  logBreadkpoint((BreakpointEvent) event);
                    if (event instanceof VMDisconnectEvent) runable = false; // 断开链接肯定是最后一个事件，所以自动跳出最内层循环
                }
                eventSet.resume();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void logBreadkpoint(BreakpointEvent event) throws Exception{
        ThreadReference threadReference = event.thread();
        StackFrame stackFrame = threadReference.frame(0);

        logger.log("<<================<< " + stackFrame.location()
        		+ " / " + stackFrame.location().method().name()
        		+ " >>================>>");
        logger.log("[方法参数]");
        List<Value> argsList = stackFrame.getArgumentValues();
        for (Value arg : argsList){
            logger.log(arg.type().name() + " = " + parseValue(arg));
        }

        logger.log("[变量信息]");
        try {
            List<LocalVariable> varList = stackFrame.visibleVariables();
            for(LocalVariable localVariable : varList){
                logLocalVariable(stackFrame, localVariable);
            }
        } catch (AbsentInformationException e) {
            throw new RuntimeException(e);
        }
    }

    private void logLocalVariable(StackFrame stackFrame, LocalVariable localVariable){
        StringBuilder out = new StringBuilder(localVariable.name());
        if(localVariable.isArgument()){
            out.append("[*]");
        }
        out.append(" = ");
        out.append(parseValue(stackFrame.getValue(localVariable)));
        logger.log(out.toString());
    }

    private String parseValue(Value value){
        StringBuilder out = new StringBuilder();
        if(value instanceof StringReference || value instanceof IntegerValue || value instanceof BooleanValue
                || value instanceof ByteValue || value instanceof CharValue || value instanceof ShortValue
                || value instanceof LongValue || value instanceof FloatValue || value instanceof DoubleValue){
            out.append(value);
        }else if(value instanceof ObjectReference){
            ObjectReference obj = (ObjectReference) value;
            String type = obj.referenceType().name();
            if("java.lang.Integer".equals(type) || "java.lang.Boolean".equals(type) || "java.lang.Float".equals(type)
                    || "java.lang.Double".equals(type) || "java.lang.Long".equals(type) || "java.lang.Byte".equals(type)
                    || "java.lang.Character".equals(type)){

                Field f = obj.referenceType().fieldByName("value");
                out.append(obj.getValue(f));

            }else if("java.util.Date".equals(type)) {

                Field field = obj.referenceType().fieldByName("fastTime");
                Date date = new Date(Long.parseLong("" + obj.getValue(field)));
                out.append(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date));

            }else if(value instanceof ArrayReference){
                ArrayReference ar = (ArrayReference) value;
                List<Value> values = ar.getValues();
                out.append("[");
                for (int i = 0; i < values.size(); i++){
                    if( i != 0 ) out.append(" ,");
                    out.append(parseValue(values.get(i)));
                }
                out.append("]");
            }else {
                out.append(type);
            }
        }
        return out.toString();
    }

}

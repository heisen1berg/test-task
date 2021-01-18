package core.handle.calculate;


import javax.script.*;

class FunctionExecutor {

    public static Object execute(String function, int arg) {
        final ScriptEngineManager scriptEngineManager = new ScriptEngineManager();
        final ScriptEngine scriptEngine = scriptEngineManager.getEngineByName("JavaScript");
        final Bindings bindings = scriptEngine.getBindings(ScriptContext.ENGINE_SCOPE);
        try {
            bindings.clear();
            bindings.put("arg", arg);
            scriptEngine.eval(function);
            return scriptEngine.get("result");
        } catch (ScriptException e) {
            e.printStackTrace();
            return "error";
        }

    }
}

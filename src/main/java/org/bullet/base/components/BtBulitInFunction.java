package org.bullet.base.components;

import org.bullet.base.types.BtType;
import org.bullet.exceptions.BulletException;
import org.bullet.interpreter.BulletRuntime;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * @author Huyemt
 */

public class BtBulitInFunction extends BtFunction {
    public final LinkedHashMap<String, Object> args = new LinkedHashMap<>();
    public boolean isVarParam = false;

    public BtBulitInFunction(String funcName, BulletRuntime runtime) {
        super(funcName, runtime);
    }

    @Override
    public final Object invokeFV(Object... args) throws BulletException {
        if (isVarParam) {
            ArrayList<Object> list = new ArrayList<>();
            for (Object arg : args) {
                list.add(BtType.toBType(arg));
            }

            return eval(list.toArray());
        }

        LinkedHashMap<String, Object> g = new LinkedHashMap<>();

        if (args.length < this.args.size()) {
            String[] params = this.args.keySet().toArray(String[]::new);

            for (int i = 0; i < args.length; i++) {
                g.put(params[i], BtType.toBType(args[i]));
            }

            for (int i = args.length; i < this.args.size(); i++) {
                Object r = this.args.get(params[i]);
                if (r == null) {
                    throw new BulletException(String.format("Missing parameter \"%s\"", params[i]));
                }

                g.put(params[i], r);
            }
        } else if (args.length == this.args.size()) {
            int i = 0;
            for (String name : this.args.keySet()) {
                g.put(name, BtType.toBType(args[i]));
                i++;
            }
        } else {
            throw new BulletException(String.format("Parameter number exceeds the maximum length of %d", this.args.size()));
        }

        return eval(g);
    }

    public Object eval(LinkedHashMap<String, Object> args) throws BulletException {
        return null;
    }

    public Object eval(Object[] args) throws BulletException {
        return null;
    }
}

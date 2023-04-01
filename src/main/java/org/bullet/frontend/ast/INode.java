package org.bullet.frontend.ast;

import org.bullet.frontend.results.Result;

/**
 * @author Huyemt
 */

public abstract class INode {
    /**s
     * 接受节点访问者进行访问<br><br>
     *
     * Receive access from node visitors
     *
     */
    public abstract Result<?> accept(IVisitor visitor);
}

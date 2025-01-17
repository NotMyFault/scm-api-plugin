/*
 * The MIT License
 *
 * Copyright (c) 2016 CloudBees, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package jenkins.scm.impl;

import hudson.Extension;
import hudson.model.Action;
import hudson.model.Item;
import hudson.scm.SCM;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import edu.umd.cs.findbugs.annotations.NonNull;
import jenkins.model.TransientActionFactory;
import jenkins.scm.api.SCM2;
import jenkins.triggers.SCMTriggerItem;

/**
 * Extension to inject the actions of a {@link SCM2} into its owning item.
 * @since 2.0
 */
@Extension
public class SCM2TransientActionFactory extends TransientActionFactory<Item> {

    /**
     * {@inheritDoc}
     */
    @Override
    public Class<Item> type() {
        return Item.class;
    }

    /**
     * {@inheritDoc}
     */
    @NonNull
    @Override
    public Collection<? extends Action> createFor(@NonNull Item target) {
        if (target instanceof SCMTriggerItem) {
            final SCMTriggerItem item = (SCMTriggerItem) target;
            List<Action> result = new ArrayList<Action>();
            for (SCM scm : item.getSCMs()) {
                if (scm instanceof SCM2) {
                    result.addAll(((SCM2) scm).getItemActions(item));
                }
            }
            return result;
        }
        return Collections.emptyList();
    }
}

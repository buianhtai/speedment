/*
 *
 * Copyright (c) 2006-2020, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.common.codegen.internal.java.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.trait.HasName;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasName}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasNameView<M extends HasName<M>> extends Transform<M, String> {
    
    /**
     * Returns the trailing suffix that should be appended after the name if
     * one is rendered.
     * <p>
     * The default value is an empty string ("").
     * 
     * @return  the trailing name suffix
     */
    default String nameSuffix() {
        return "";
    }
    
    /**
     * Render the name of the model.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderName(Generator gen, M model) {
        return model.getName() + nameSuffix();
    }
}
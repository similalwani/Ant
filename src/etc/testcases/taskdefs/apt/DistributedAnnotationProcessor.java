package etc.testcases.taskdefs.apt;

/*
 *  Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

//found in tools.jar, not the JRE runtime.
import com.sun.mirror.apt.AnnotationProcessor;
import com.sun.mirror.apt.AnnotationProcessorEnvironment;
import com.sun.mirror.declaration.TypeDeclaration;
import com.sun.mirror.declaration.ClassDeclaration;
import com.sun.mirror.util.SimpleDeclarationVisitor;
import static com.sun.mirror.util.DeclarationVisitors.*;

import java.util.Map;

/**
 * Annotation processor outputs stuff
 */

public class DistributedAnnotationProcessor implements AnnotationProcessor {

    public AnnotationProcessorEnvironment env;

    public DistributedAnnotationProcessor(AnnotationProcessorEnvironment env) {
        this.env = env;
    }

    public void echo(String text) {
        env.getMessager().printNotice(text);
    }

    public void process() {
        echo("DistributedAnnotationProcessor-is-go");

        Map<String, String> options=env.getOptions();
        for(String key:options.keySet()) {
            echo("Option ["+key+"] = "+options.get(key));
        }

        //work time
        for (TypeDeclaration typeDecl : env.getSpecifiedTypeDeclarations()) {
            typeDecl.accept(getDeclarationScanner(new ClassVisitor(),
                    NO_OP));
        }
    }

    private class ClassVisitor extends SimpleDeclarationVisitor {
        public void visitClassDeclaration(ClassDeclaration d) {
            echo("visiting "+ d.getQualifiedName());
        }
    }
}

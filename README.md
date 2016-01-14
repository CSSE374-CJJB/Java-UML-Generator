# Java-UML-Generator
Generates UML Diagrams for Java Code using ASM

It is built around using ASM's ClassVisitor with custom decorators.

# Usage Instructions

Download and compile the project. Should show up as an eclipse project if you import it.

In Main.Java the Static Classes Variable add the classes you want to show up on the UML. You can also add based on packages to the PACKAGES variable and it will pull all classes from that package.

!! The class names you select must also be in the class path.

The output goes to output.txt in the same folder that the program was run from.

# Design Over Time
Milestone 1: 

<img src="https://raw.githubusercontent.com/CSSE374-CJJB/Java-UML-Generator/master/docs/ASM_UML_Generated.png"/>

Used decorators of type ClassVisitor to create the dot file as the visitor progressed

Pair Programed it all.

Milestone 2: 

<img src="https://raw.githubusercontent.com/CSSE374-CJJB/Java-UML-Generator/master/docs/ASM_UML_GENERATED_M2.png"/>

Exapanded upon our Milestone one with just a few more methods and fields in Relations and the visitors.
Have desided that we need to do a redesign with the information that we now know about how ASM works and the future of the project. We plan to make a DataStruct for all elements of the dot diagram that is created from our original ClassVisitor design. After the data structure has been created use another visitor to go through that data structure and create the dot file.

Pair Programed it all (Its all we do).

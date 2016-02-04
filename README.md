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

MileStone 3:

<img src="https://raw.githubusercontent.com/CSSE374-CJJB/Java-UML-Generator/master/docs/UML_milestone3_auto_generated.png"/>

Our new full redesign has taken place. We now have a model object that has a structure that is intricate enough to hold all the information that we need about a java class and call structure while still being fexible enough to add aditional features without much effort. 

Our original class visitors are still around but where we were printing to the output is now exapanding our model. After a model is created we can then pass our UML or Sequence visitor to it and it will generate the desired result.

Adding the ability to do sequence digrams required us to make a new type of visitor for it. To be able to just refence and class method and depth also required us to make a recursive Method visitor. These extended our normal visitors. Our normal visitors would work for a sequence diagram like a whitelist while this new one would just grab everything that was called to the given depth.

We Pair programmed at the start but after 30+ hours working on it we split up one to work on the code while the other wrote test cases for that code. After the first deadline passed we both just worked on it as much as we could to figgure out the bugs with how method names where being inialized. 


MileStone 4:

<img src="https://raw.githubusercontent.com/CSSE374-CJJB/Java-UML-Generator/master/docs/UML_milestone4.PNG"/>

We added the capability of checking for the Singleton class instance by adding an Interface that implements a check method and by adding a class that implements that method specifically for the Singleton case.  This should allow easy adding for further class checks of this type.  

Besides the addition of the above interface and class, no design changes from MileStone 3 had to be made.

We pair programmed for the beginning of MileStone 4 and discussed how we wanted to implement the check for the UML diagram.  After deciding how we wanted to do it and we did a little pair programming, we split off for finishing MileStone 4 code and test cases.


MileStone 5:

<img src="https://raw.githubusercontent.com/CSSE374-CJJB/Java-UML-Generator/master/docs/UML_MANUAL_M5.PNG"/>

We added pattern detection and built it around a pattern to allow an arbitary amount of checks to be added that can be easily changed via the factory or by creating your own list of IPatternCheck and sending it to the finalize method of JavaModel.

We did change our design single last milestone to refactor out our singletoncheck to make it follow our new pattern, but that did not take much time at all as it was already very abstract and just became a preprocess instead of inline.

Milestone 5 went much the same as Milestone 4 for our programming split. We talke about how we wanted to design the system and then after most of the implementation was done split into finishing the code and test cases.

# NF Modeling
The project consists in a modeling framework suitable for custom Virtual Network Functions (VNFs). Once a network function model has been developed and compiled by the (included) parser, then the corresponding first order logic formulas are produced as output and they can be used as input for verification with the Z3 sat solver along with the rest of the network model already implemented by Panda et al. (see http://arxiv.org/abs/1409.7687 for reference) and extended by Spinoso et al. (see http://link.springer.com/chapter/10.1007%2F978-3-319-24072-5_18 for further reading).

## Toolkit
The toolkit is composed by three main parts:

	- a modeling library that can be used to write the model of a generic VNF (package it.polito.modelib);
	
	- a parser that is able to analyze the VNF code (package it.polito.parser);
	
	- a set of examples of VNF models (package it.polito.NFModels).

## Run
TODO: Further instruction on how to run the tool will be provided in the future.
```sh
$ java -jar ...
```

## Note
You can directly import this repo into the Eclipse IDE since it already is an Eclipse project, with Maven behind the scene to manage dependencies. A test main is available under it.polito.parser (Parser.java class).
The "target" folder (usually available in Maven project) has been untracked in order not to upload compiled .class files on the repo so you will have to build the code on your own.

Eclipse version: Eclipse Java EE IDE for Web Developers.
Version: Mars.1 Release (4.5.1)
Build id: 20150924-1200

Java version: 1.8.0_65 (Java HotSpot 64-bit Server VM build 25.65-b01)

## For comments and feedbacks, please contact matteo.virgilio@polito.it
package edu.hendrix.csci250.csci250proj3;

public enum LearningDomain {
	VA {
		public String shortName() {
			return "VA";
		}
		
		public String longName() {
			return "Values, Beliefs and Ethics";
		}
		
		public String description() {
			return "A perennial feature of humanity is the ability and need to raise fundamental questions about the ultimate meaning of our existence, our common origins and destiny, the nature of reason, and what constitutes a good life. Our efforts to deal with these questions reflect basic values and beliefs that shape our perception of the world, give order and purpose to our existence, and inform our moral judgment. Courses in this domain seek to explore critically and to understand different value and belief systems, to examine commonalities of these systems across historical, philosophical, religious, and/or cultural boundaries, and to introduce ways of making reasoned value judgments.";
		}
	},
	SB {
		public String shortName() {
			return "SB";
		}
		
		public String longName() {
			return "Social and Behavioral Analysis";
		}
		public String description() {
			return "Human experience always takes place in the context of larger social forces, organizations, and institutions: families, organizations, communities, governments, and economics. Courses in this domain study the myriad dimensions of human behavior and the human relationships from a variety of disciplinary and interdisciplinary perspectives. Through this study we begin to comprehend individual and social life and to develop policies and other means of intervention.";
		}
	},
	NS_L {
		public String shortName() {
			return "NS-L";
		}
		
		public String longName() {
			return "Natural Science Inquiry (With Lab)";
		}
		
		public String description() {
			return "Science and technology are playing an ever-increasing role in our society. In order to navigate this information students must know and understand how science does and does not work, the application of scientific and mathematical principles, and the distinction between science and dogma. This requires the coupling of basic scientific principles with systematic, critical analysis. Emphasis is on the methods used to model, gather, interpret, and evaluate data critically and the placement of this information into a larger context. In the face of our rapidly evolving understanding of the natural world, application of the scientific method is an enduring skill for assessing the validity of observations related to the natural world. This mode of inquiry inextricably links course content and the analysis process.";
		}
	},
	NS {
		public String shortName() {
			return "NS";
		}
		
		public String longName() {
			return "Natural Science Inquiry";
		}
		
		public String description() {
			return "Science and technology are playing an ever-increasing role in our society. In order to navigate this information students must know and understand how science does and does not work, the application of scientific and mathematical principles, and the distinction between science and dogma. This requires the coupling of basic scientific principles with systematic, critical analysis. Emphasis is on the methods used to model, gather, interpret, and evaluate data critically and the placement of this information into a larger context. In the face of our rapidly evolving understanding of the natural world, application of the scientific method is an enduring skill for assessing the validity of observations related to the natural world. This mode of inquiry inextricably links course content and the analysis process.";
		}
	},
	LS {
		public String shortName() {
			return "LS";
		}
	
		public String longName() {
			return "Literary Studies";
		}
		
		public String description() {
			return "Literature has been a central form of expression for many societies. Literature provides a medium through which students gain insight into the minds and lives of other human beings and the process whereby human experience is imaginatively transformed into art. Critical reading/interpretation of a literary text provides understanding into what meanings that text holds, how those meanings are produced, what purposes they serve, and what effects they have. Literary studies also facilitate a student’s ability to articulate responses both orally and in writing.";
		}
	},
	HP {
		public String shortName() {
			return "HP";
		}
		
		public String longName() {
			return "Historical Perspective";
		}
		
		public String description() {
			return "History is that branch of knowledge that seeks to account for the diverse ways in which human beings in different cultures and societies have all responded to temporal change. Through the examination of contemporary issues from a historical perspective, we gain insight into the richness of human experience and gain insight into our own convictions and actions. Courses in this domain study the development of societies and cultures over time.";
		}
	},
	EA {
		public String shortName() {
			return "EA";
		}
		
		public String longName() {
			return "Expressive Arts";
		}
		
		public String description() {
			return "Throughout history, humans have used the arts to explore and express ideas and feelings in a uniquely symbolic and expressive way, endowing the arts with qualities that are significantly different from those embodied in other ways of knowing. To understand any culture, a person must be able to grasp, interpret, and respond to its artistic creations and symbols. Given the broad spectrum of cultural production, a study of the expressive arts introduces students to ways of interpreting and understanding art content, as well as understanding the forms through which this content is produced and communicated. Courses in this domain emphasize either the creative process through the making and performing of works of art or the place of such works of art within a particular historical, cultural, or aesthetic context.";
		}
	}
}

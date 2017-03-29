package edu.hendrix.csci250.csci250proj3;

public enum Capacity {
	W1 {
		public String shortName() {
			return "W1";
		}
		
		public String longName() {
			return "Writing Level 1";
		}
		
		public String description() {
			return "Clear and effective writing is inseparable from clear and coherent thinking. Each student must demonstrate the attainment of an acceptable level of skill in written communication by fulfilling the requirements of a bi-level writing program.";
		}
	},
	W2 {
		public String shortName() {
			return "W2";
		}
		
		public String longName() {
			return "Writing Level 2";
		}
		
		public String description() {
			return "Clear and effective writing is inseparable from clear and coherent thinking. Each student must demonstrate the attainment of an acceptable level of skill in written communication by fulfilling the requirements of a bi-level writing program.";
		}
	},
	QS {
		public String shortName() {
			return "QS";
		}
		
		public String longName() {
			return "Quantitative Studies";
		}
		
		public String description() {
			return "As our society becomes more technologically and analytically based, it is important that students develop quantitative skills that are necessary in a large and growing number of careers. Mathematical models form the basis for many fundamental concepts and modes of analysis in a diverse number of disciplines. Students need to possess sufficient quantitative skills in order to understand, manipulate, and interpret these models. It is, therefore, important that students possess a base level of mathematical/computing skills necessary for the development of those quantitative skills they will need in their chosen disciplines and in their lives.";
		}
	},
	FL {
		public String shortName() {
			return "FL";
		}
		
		public String longName() {
			return "Foreign Language";
		}
		
		public String description() {
			return "Students should achieve the degree of competence in a foreign language necessary to encounter another culture on its own terms. This level of ability requires being able to understand, analyze, and use a foreign language. Such a capacity increases subtlety of mind, sharpens sensitivity to the use of one’s own language, and more fully opens another culture for exploration.";
		}
	},
	PA {
		public String shortName() {
			return "PA";
		}
		
		public String longName() {
			return "Physical Activity";
		}
		
		public String description() {
			return "Students are encouraged to develop and practice a lifestyle that promotes wellness, physical fitness and incorporates recreational activities on a regular basis.";
		}
	}
}

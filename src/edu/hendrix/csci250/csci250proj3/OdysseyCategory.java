package edu.hendrix.csci250.csci250proj3;

public enum OdysseyCategory {
	AC {
		public String shortName() {
			return "AC";
		}
		public String longName() {
			return "Artistic Creativity";
		}
		public String description() {
			return "Experiences in which students explore their creative potential in art, music, dance, drama, film, or creative writing.";
		}
	},
	GA {
		public String shortName() {
			return "GA";
		}
		public String longName() {
			return "Global Awareness";
		}
		public String description() {
			return "Experiences in which students immerse themselves in cultures or environments other than their own in ways that enhance their appreciation of those cultures and environments, deepen their understanding of the major issues affecting the world today, and lend them new perspectives on the places in which they live.";
		}
	},
	PL {
		public String shortName() {
			return "PL";
		}
		public String longName() {
			return "Professional and Leadership Development";
		}
		public String description() {
			return "Experiences in which students apply their intellectual interests through internships, other opportunities for working alongside professionals on site, or leadership in community life or professional settings.";
		}
	},
	SW {
		public String shortName() {
			return "SW";
		}
		public String longName() {
			return "Service to the World";
		}
		public String description() {
			return "Experiences within and beyond the Hendrix community in which students are engaged in helping meet the social, ecological and spiritual needs of our time.";
		}
	},
	UR {
		public String shortName() {
			return "UR";
		}
		public String longName() {
			return "Undergraduate Research";
		}
		public String description() {
			return "Experiences in which students undertake significant research projects using the methods of their chosen disciplines.";
		}
	},
	SP {
		public String shortName() {
			return "SP";
		}
		public String longName() {
			return "Special Projects";
		}
		public String description() {
			return "Experiences in which students extend, apply, connect or share different ways of knowing (e.g., oral, verbal, tactile, imaginative, intuitive), often in interdisciplinary settings.";
		}
	}
}

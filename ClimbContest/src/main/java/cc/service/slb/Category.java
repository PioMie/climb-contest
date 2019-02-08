package cc.service.slb;

public enum Category {

	PRO_MALE("PM"), HARD_MALE("TM"), EASY_MALE("LM"), VETERAN_MALE("WM"), JUNIOR_MALE("JM"), HARD_FEMALE("TK"),
	EASY_FEMALE("LK"), VETERAN_FEMALE("WK"), JUNIOR_FEMALE("JK"), NONE("");

	private String code;

	private Category(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code;
	}

	public static Category getByCode(String code) {
		for (Category category : Category.values()) {
			if (category.code.equals(code)) {
				return category;
			}
		}
		return NONE;
	}

	public static Category mapCategory(String category) {
		switch (category) {
		case "PRO MALE":
			return PRO_MALE;
		case "HARD MALE":
			return HARD_MALE;
		case "EASY MALE":
			return EASY_MALE;
		case "VETERAN MALE":
			return VETERAN_MALE;
		case "JUNIOR MALE":
			return JUNIOR_MALE;
		case "HARD FEMALE":
			return HARD_FEMALE;
		case "EASY FEMALE":
			return EASY_FEMALE;
		case "VETERAN FEMALE":
			return VETERAN_FEMALE;
		case "JUNIOR FEMALE":
			return JUNIOR_FEMALE;
		default:
			return NONE;
		}
	}
}

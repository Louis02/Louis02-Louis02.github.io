
public class LeaderBoard implements Comparable<LeaderBoard> {
	String name;
	int score;

	public LeaderBoard(String name, int score) {

		this.name = name;
		this.score = score;
	}

	public String getName() {
		return name;
	}

	public int getScore() {
		return score;
	}

	@Override
	public int compareTo(LeaderBoard other) {
		// TODO Auto-generated method stub
		if (score > other.getScore()) {
			return -1;
			
		} else if (score < other.getScore()) {
			return 1;
			

		} else {
			return 0;
			
		}
	}

}
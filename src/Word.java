public class Word implements Comparable<Word> {
	String eng;
	String kor;
	int flag = 0;
	int wrong = 0;
     int count =0;
	public Word(String eng, String kor) {
		super();
		this.eng = eng;
		this.kor = kor;
	}
	public Word(String eng, String kor, int flag, int wrong) {
		super();
		this.eng = eng;
		this.kor = kor;
		this.flag = flag;
		this.wrong = wrong;
	}
	public Word(String word, String meaning, int count){
		this(word, meaning);
		this.count = count;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return eng + " : " + kor;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		if(obj instanceof Word) {
			Word temp = (Word)obj;
			return this.eng == temp.eng && this.kor == temp.kor;
		}
		else
			return super.equals(obj);
	}

	@Override
	public int hashCode() {
		// TODO Auto-generated method stub
		return eng.hashCode() + kor.hashCode();
	}

	@Override
	public int compareTo(Word o) {
		// TODO Auto-generated method stub
		return (this.flag - o.flag)*-1;
	}


}
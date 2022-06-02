import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

@SuppressWarnings("serial")
public class MyFrame extends JFrame {

	//생성자
	HashMap<String, Word> map = new HashMap<>();
	HashMap<String, Word> map2 = new HashMap<>();
	List<Word> list = new ArrayList<>();
	ArrayList<Word> wrongWordList = new ArrayList<>();
	List<Word> list3 = new ArrayList<>();
	JLabel label2;
	JButton[] btn = new JButton[6];
	String[] names = {"1. 단어장 보기", "2. 단어 뜻 검색", "3. 객관식 퀴즈", "4. 오답노트", "5.단어추가", "6.종료"};
	Color color = new Color(199, 211, 212);
	Color color2 = new Color(74, 78, 88);
	Font font = new Font("Gowun Batang", Font.BOLD, 15);
	JTextArea text = new JTextArea(50, 50);
	JScrollPane scroll = new JScrollPane(text);
	String filePath;
	String wrongfilePath = "./src/wrongWord.txt";
	private int number = 0;
	//나중에 크기고려
	private Word[] voc = new Word[100];

	public MyFrame() {
		this("202111391 황수빈");
	}

	public MyFrame(String title) {
		// TODO Auto-generated constructor stub
		super(title);
		this.add(new MenuPane());
		this.setSize(450, 600);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setVisible(true);
	}

	//메뉴출력
	public class MenuPane extends JPanel {
		public MenuPane() {

			setLayout(new GridBagLayout());
			setBorder(new EmptyBorder(30, 30, 30, 30));

			GridBagConstraints gbc = new GridBagConstraints();

			gbc.gridwidth = GridBagConstraints.REMAINDER;
			gbc.anchor = GridBagConstraints.NORTH;
			gbc.insets = new Insets(5, 5, 5, 5);

			setBackground(color);

			JLabel label1 = new JLabel("나만의 영어 단어장");
			label1.setForeground(color2);
			label1.setOpaque(true);
			ImageIcon img = new ImageIcon("img/m.png");
			//ImageIcon 의 크기 조절
			Image ii = img.getImage();
			Image newImage = ii.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
			ImageIcon newIcon = new ImageIcon(newImage);
			JLabel imageLabel = new JLabel(newIcon);

			label2 = new JLabel("선택된 파일이 없어요. 파일을 가져와주세요");
			label2.setForeground(color2);

			JButton openButton = new JButton("File 가져오기");
			openButton.setFont(font);
			openButton.setBackground(color);
			openButton.addActionListener(new OpenActionListener());

			label1.setFont(new Font("Gowun Batang", Font.BOLD, 26));
			label1.setBackground(color);
			label2.setBackground(color);
			label2.setFont(font);
			add(label1, gbc);
			add(imageLabel, gbc);
			add(label2, gbc);
			add(openButton, gbc);

			gbc.anchor = GridBagConstraints.CENTER;
			gbc.fill = GridBagConstraints.HORIZONTAL;

			for (int i = 0; i < btn.length; i++) {
				btn[i] = new JButton(names[i]);
				btn[i].setFont(new Font("Gowun Batang", Font.BOLD, 18));
				btn[i].setForeground(color2);
				btn[i].addActionListener(new MyActionListener());
				add(btn[i], gbc);
			}
		}

		class MyActionListener implements ActionListener {

			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton) e.getSource();
				if (b.getText().equals(names[0])) {
					seeWord();
				} else if (b.getText().equals(names[1])) {
					searchVoc();
				} else if (b.getText().equals(names[2])) {
					Quiz mq = new Quiz();
				} else if (b.getText().equals(names[3])) {
					note n = new note();
				} else if (b.getText().equals(names[4])) {
					add a = new add();
				} else if (b.getText().equals(names[5])) {
					System.exit(0);
				}
			}
		}

		public class MyDialog extends JDialog {
			MyDialog(String title) {
				setTitle(title);
				setSize(500, 500);
				setVisible(true);
			}
		}

		//단어장 보기
		void seeWord() {
			if (filePath != null) {
				MyDialog swdial = new MyDialog("단어장 보기");
				scroll.setBackground(color);
				swdial.setLocation(500, 200);
				swdial.add(scroll);
				swdial.setVisible(true);
			} else JOptionPane.showMessageDialog(null, "파일을 먼저 가져와주세요");
		}

		//단어 뜻 검색
		void searchVoc() {
			if (filePath != null) {
				setBackground(color);
				JPanel a = new JPanel();
				JPanel b = new JPanel();
				a.setBackground(color);
				b.setBackground(color);
				JTextField tf = new JTextField(20);
				JTextField tf1 = new JTextField(20);
				MyDialog schdial1 = new MyDialog("단어 뜻 검색");
				schdial1.setLocation(500, 200);
				schdial1.setSize(500, 100);
				schdial1.setLayout(new BorderLayout());
				JLabel label1 = new JLabel("검색할 단어를 입력하세요");
				label1.setFont(font);
				a.add(label1);
				a.add(tf);
				schdial1.add(a, BorderLayout.NORTH);
				JLabel label2 = new JLabel("단어 뜻");
				label2.setFont(font);
				b.add(label2);
				b.add(tf1);
				tf.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						JTextField t = (JTextField) e.getSource();
						String sWord = t.getText();
						sWord = sWord.trim();
						if (map.get(sWord) == null) {
							JOptionPane.showMessageDialog(null, "단어장에 등록되어 있지 않습니다.");
						}
						for (Word word : voc) {
							if (word != null) {
						 		if (word.eng.equals(sWord)) {
									tf1.setText(word.kor);
									break;

								}
							}
						}
					}
				});
				schdial1.add(b, BorderLayout.AFTER_LAST_LINE);
				schdial1.setBackground(color);
				schdial1.setVisible(true);
			} else JOptionPane.showMessageDialog(null, "파일을 먼저 가져와주세요");
		}

		//오답노트
		public class note extends JFrame {
			JPanel panel = new JPanel();
			JLabel jl1 = new JLabel("오답노트");
			JLabel jl2 = new JLabel("단어");
			JTextArea ta = new JTextArea();

			note() {
				setTitle("오답노트");
				setSize(600, 500);
				setLocation(95, 100);
				init();
				setVisible(true);
				setContentPane(panel);
			}

			void init() {
				panel.setLayout(null);
				panel.setBackground(color);
				jl1.setLocation(260, 10);
				jl1.setFont(new Font("Gowun Batang", Font.BOLD, 24));
				jl1.setSize(300, 50);
				jl2.setLocation(50, 100);
				jl2.setSize(30, 20);
				jl2.setFont(font);
				panel.add(jl1);
				panel.add(jl2);
				ta.setSize(300, 150);
				ta.setFont(font);
				ta.setLocation(50, 100);

				for (int i = 0; i < list.size(); i++) {
					if (list.get(i).wrong > 0)
						ta.append("틀린횟수 : " + list.get(i).wrong + " 단어: " + list.get(i).eng + " 단어의 뜻: " + list.get(i).kor + "\n");
				}

				JScrollPane scroll = new JScrollPane(ta);
				scroll.setLocation(150, 100);
				scroll.setSize(400, 300);
				panel.add(scroll);
			}
		}


		public void addWrongWord(String word, String meaning) {

			File file = new File("./src/wrongWord.txt");
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));) {
				bw.write("\n" + word + "\t" + meaning);
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				File f = new File("./src/wrongWord.txt");
				BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f)));
				String line = "";
				String s = "";
				while ((s = br.readLine()) != null) {
					line += s + "\n";
					String[] temp = s.split("\t");
					Word temp_word = new Word(temp[0].trim(), temp[1].trim(), 0, 0);
					wrongWordList.add(temp_word);
				}
			} catch (Exception e2) {
			}
		}

		// 단어 추가
		public class add extends JFrame {
			JPanel panel = new JPanel();
			JTextField t1 = new JTextField(20);
			JTextField t2 = new JTextField(20);
			JLabel lb = new JLabel("단어 추가");
			JLabel label = new JLabel("단어");
			JLabel label2 = new JLabel("단어의 뜻");
			JButton btn = new JButton("단어추가");

			add() {
				if (filePath != null) {
					this.setTitle("단어 추가");
					this.setSize(400, 350);
					this.setLocation(120, 50);
					this.setVisible(true);
					init();
					this.add(panel);
				} else JOptionPane.showMessageDialog(null, "파일을 먼저 가져와주세요");
			}

			void init() {
				panel.setLayout(null);
				panel.setBackground(color);
				lb.setFont(new Font("Gowun Batang", Font.BOLD, 24));
				lb.setLocation(150, 10);
				lb.setSize(100, 50);
				t1.setLocation(180, 80);
				t1.setSize(100, 30);
				t2.setLocation(180, 130);
				t2.setSize(100, 30);
				label.setLocation(140, 80);
				label.setSize(100, 30);
				label.setFont(font);
				label2.setLocation(110, 130);
				label2.setSize(100, 30);
				label2.setFont(font);
				btn.setFont(font);
				btn.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						// TODO Auto-generated method stub
						if ((t1.getText().trim().equals("") || t2.getText().trim().equals(""))) {
							JOptionPane.showMessageDialog(null, "텍스트 상자가 제대로 입력되지 않았습니다.");
						} else {
							JOptionPane.showMessageDialog(null, "단어가 추가되었습니다.");

							try {
								BufferedWriter bw = new BufferedWriter(new FileWriter(new File(filePath), true));
							} catch (IOException ex) {
								ex.printStackTrace();
							}
							addNewWord(t1.getText().trim(), t2.getText().trim());
							map.put(t1.getText().trim(), new Word(t1.getText().trim(), t2.getText().trim(), 0, 0));
							text.append(t1.getText().trim() + "\t" + t2.getText().trim() + "\n");
							list.add(new Word(t1.getText().trim(), t2.getText().trim()));
							list3.add(new Word(t2.getText().trim(), t1.getText().trim()));
							map2.put(t2.getText().trim(), new Word(t2.getText().trim(), t1.getText().trim(), 0, 0));
						}
					}
				});
				btn.setLocation(150, 200);
				btn.setSize(100, 30);
				panel.add(label);
				panel.add(label2);
				panel.add(btn);
				panel.add(lb);
				panel.add(t1);
				panel.add(t2);
			}
		}

		//텍스트 파일 단어 업데이트
		public void addNewWord(String word, String meaning) {
			list.add(new Word(word, meaning));
			File file = new File(filePath);
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true));) {
				bw.write("\n" + word + "\t" + meaning);
				bw.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		//객관식 퀴즈
		public class Quiz extends JFrame implements ItemListener {
			JPanel panel = new JPanel();
			JRadioButton[] btn = new JRadioButton[4];
			JLabel label2 = new JLabel();
			int b;
			int count = 0;
			int flag1 = 0;

			public Quiz() {
				if (filePath != null) {
					setBackground(color);
					setTitle("객관식 퀴즈");
					setLayout(null);
					setLocation(500, 200);
					setSize(500, 500);
					multiple();
					setVisible(true);
				} else JOptionPane.showMessageDialog(null, "파일을 먼저 가져와주세요");
			}

			//시작시간
			long start = System.currentTimeMillis();
			String multi[] = new String[4];
			Random random = new Random();
			int a;

			void multiple() {
				setBackground(color);
				setContentPane(panel);
				panel.setLayout(null);
				JLabel label1 = new JLabel("객관식 퀴즈");
				label1.setBackground(color);
				label1.setFont(new Font("Gowun Batang", Font.BOLD, 26));
				label1.setLocation(190, 40);
				label1.setSize(200, 50);
				panel.add(label1);

				b = (int) random.nextInt(4);        //객관식 답 방
				//객관식 퀴즈 생성
				for (int t = 0; t < 4; t++) {
					multi[t] = null;
				}
				a = (int) random.nextInt(number);    //랜덤 영어문제
				list.get(a).flag++;
				label2 = new JLabel(list.get(a).eng + "의 뜻은 무엇일까요?");
				label2.setVisible(true);
				label2.setFont(font);
				label2.setLocation(170, 100);
				label2.setSize(300, 30);
				panel.add(label2);

				//객관식 답 생성
				list.add(new Word(list.get(a).eng, list.get(a).kor));
				while (true) {
					multi[b] = list.get(a).kor;
					for (int k = 0; k < 4; k++) {
						int c = (int) random.nextInt(number);
						if (k == b) {
							continue;
						} else {
							boolean s = false;
							for (int j = 0; j < 4; j++) {
								if (multi[j] != null)
									continue;
								if (list.get(c).kor.equals(list.get(j).kor)) {
									k--;
									s = true;
									break;
								}
							}
							if (!s) {
								multi[k] = list.get(c).kor;
							}
						}
					}
					int r = 0;
					for (int h = 0; h < 4; h++) {
						for (int j = 0; j < h; j++) {
							if (multi[h].equals(multi[j])) {
								r++;
							}
						}
					}
					if (r == 0)
						break;
				}

				for (int q = 0; q < btn.length; q++) {
					btn[q] = new JRadioButton(multi[q]);
					btn[q].setLocation(170, 140 + 40 * q);
					btn[q].setSize(200, 40);
					btn[q].addItemListener(this);
					btn[q].setFont(font);
					panel.add(btn[q]);
				}
				flag1++;
			}

			@Override
			public void itemStateChanged(ItemEvent e) {
				// TODO Auto-generated method stub
				int selected = 0;
				if (e.getStateChange() == ItemEvent.SELECTED)
					selected = 1;
				else
					selected = -1;
				if (selected == 1) {
					if (e.getItem() == btn[b]) {
						panel.removeAll();
						JOptionPane.showMessageDialog(null, "정답입니다.");
						addWrongWord(list.get(a).eng,list.get(a).kor);
						multiple();
						count++;
					} else {
						panel.removeAll();
						JOptionPane.showMessageDialog(null, "오답입니다. 정답은 " + multi[b] + " 입니다.");
						list.get(a).wrong++;
						multiple();
					}
				}
				if (flag1 == 11) {
					panel.removeAll();
					long end = System.currentTimeMillis(); // 종료시간
					long time = (end - start) / 1000; // 소요시간
					JOptionPane.showMessageDialog(null, "10문제 중 " + count + "개를 맞추셨고, 총 " + time + "초 소요되었습니다.");
					dispose();
				}
			}
		}

		//file 가져오기
		class OpenActionListener implements ActionListener {

			JFileChooser chooser;

			OpenActionListener() {
				chooser = new JFileChooser();
			}

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
				chooser.setFileFilter(filter);
				int ret = chooser.showOpenDialog(null);
				if (ret == JFileChooser.APPROVE_OPTION) {
					try {
						File fi = chooser.getSelectedFile();
						BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(fi)));
						String line = "";
						String s = "";
						while ((s = br.readLine()) != null) {
							line += s + "\n";
							String[] temp = s.split("\t");
							Word temp_word = new Word(temp[0].trim(), temp[1].trim(), 0, 0);
							this.addWord(temp_word);
							list.add(temp_word);
							list3.add(new Word(temp[1].trim(), temp[0].trim(), 0, 0));
							map.put(temp[0].trim(), new Word(temp[0].trim(), temp[1].trim(), 0, 0));
							map2.put(temp[1].trim(), new Word(temp[1].trim(), temp[0].trim(), 0, 0));
						}
						text.setText(line);
						if (br != null)
							br.close();
						label2.setText(fi.getName() + " 파일을 불러왔어요.");
						filePath = fi.getPath();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.", "경고", JOptionPane.WARNING_MESSAGE);

					}
				}
			}

			void addWord(Word word) {
				if (number < voc.length)
					voc[number++] = word;
				else {
				}
			}
		}
	}

		public static void main(String[] args) {
			// TODO Auto-generated method stub
			new MyFrame();

		}
	}


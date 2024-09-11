package console.academy;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

public class AddressBookLogic extends JFrame implements ActionListener {
    private JButton[] mainMenuButtons;
    private JButton[] adminMenuButtons;
    private JButton[] userMenuButtons;
    private JFrame adminMenuFrame;
    private JFrame userMenuFrame;
    boolean isPlaying = false;
    MediaPlayer player = null;
    List<People> people;
    private JTextField idField;
    private JPasswordField passwordField;
    
    public AddressBookLogic() {
        setTitle("메인 메뉴"); //팝업 창 제목
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //팝업 창을 닫을 시 프로그램 종료
        setSize(300, 200); //팝업 창 크기 설정
        setLocationRelativeTo(null); //화면 가운데 위치
        people = new ArrayList<>(); 
        loadPeople(); 
        JPanel panel = new JPanel(); //메인 메뉴 패널 생성
        panel.setLayout(new GridLayout(3, 1)); //패널 레이아웃 설정
        String[] mainMenuLabels = {"관리자 계정", "일반 계정", "종료"};
        mainMenuButtons = new JButton[mainMenuLabels.length];
        for (int i = 0; i < mainMenuLabels.length; i++) {
            mainMenuButtons[i] = new JButton(mainMenuLabels[i]); //메인 메뉴 버튼 생성
            mainMenuButtons[i].addActionListener(this); 
            panel.add(mainMenuButtons[i]); //버튼 추가
        } //for
        add(panel); //패널 추가
        setVisible(true); //윈도우 표시
    } //////////////AddressBookLogic

    @Override
    public void actionPerformed(ActionEvent e) {
        //메인 메뉴 버튼
        for (int i = 0; i < mainMenuButtons.length; i++) {
            if (e.getSource() == mainMenuButtons[i]) {
                switch (i) {
                    case 0: //관리자 계정
                    case 1: //일반 계정
                        //음악 재생
                        if (!isPlaying) {
                            player = new MediaPlayer("music/music.mp3");
                            player.setDaemon(true);
                            isPlaying = true;
                            player.start();
                        }
                        //관리자 또는 사용자 메뉴를 표시
                        if (i == 0)
                            showAdminMenu();
                        else
                            showUserMenu();
                        return;
                    case 2: //종료
                        //프로그램 종료
                        System.exit(0);
                        return;
                } //switch
            }
        } //for
        //관리자 메뉴 버튼
        if (adminMenuButtons != null) {
            for (int i = 0; i < adminMenuButtons.length; i++) {
                if (e.getSource() == adminMenuButtons[i]) {
                    switch (adminMenuButtons[i].getText()) {
                        case "입력":
                            inputPeople();
                            return;
                        case "출력":
                            printPerson(adminMenuFrame);
                            return;
                        case "수정":
                            editPeople();
                            return;
                        case "삭제":
                            deletePeople();
                            return;
                        case "검색":
                            searchPeople();
                            return;
                        case "파일저장":
                            savePeople();
                            return;
                        case "음악재생":
                        	//음악 재생
                            if (!isPlaying) {
                                player = new MediaPlayer("music/music.mp3");
                                player.setDaemon(true);
                                isPlaying = true;
                                player.start();
                                return;
                            } else {
                                System.out.println("음악이 실행중입니다.");
                                return;
                            }
                        case "음악중지":
                        	//음악 재생
                            if (isPlaying) {
                                isPlaying = false;
                                player.stop_();
                                return;
                            } else {
                                System.out.println("음악이 실행중이 아닙니다.");
                                return;
                            }
                        default:
                            //메인 메뉴
                            if (isPlaying) {
                                isPlaying = false;
                                player.stop_();
                                adminMenuFrame.setVisible(false);
                                setVisible(true);
                                return;
                            } else {
                                adminMenuFrame.setVisible(false);
                                setVisible(true);
                                return;
                            }
                    } //switch
                }
            } //for
        }
        //사용자 메뉴 버튼
        if (userMenuButtons != null) {
            for (int i = 0; i < userMenuButtons.length; i++) {
                if (e.getSource() == userMenuButtons[i]) {
                    switch (userMenuButtons[i].getText()) {
                        case "출력":
                            printPerson(userMenuFrame);
                            return;
                        case "검색":
                            searchPeople();
                            return;
                        case "음악재생":
                        	//음악 재생
                            if (!isPlaying) {
                                player = new MediaPlayer("music/music.mp3");
                                player.setDaemon(true);
                                isPlaying = true;
                                player.start();
                                return;
                            } else {
                                System.out.println("음악이 실행중입니다.");
                                return;
                            }
                        case "음악중지":
                        	//음악 재생
                            if (isPlaying) {
                                isPlaying = false;
                                player.stop_();
                                return;
                            } else {
                                System.out.println("음악이 실행중이 아닙니다.");
                                return;
                            }
                        default:
                            //메인 메뉴
                            if (isPlaying) {
                                isPlaying = false;
                                player.stop_();
                                userMenuFrame.setVisible(false);
                                setVisible(true);
                                return;
                            } else {
                                userMenuFrame.setVisible(false);
                                setVisible(true);
                                return;
                            }
                    } //switch
                }
            } //for
        }
    } //////////////actionPerformed

    private void showAdminMenu() {
        //관리자 메뉴 프레임
        adminMenuFrame = new JFrame("관리자 메뉴");
        adminMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminMenuFrame.setSize(400, 300);
        adminMenuFrame.setLocationRelativeTo(null);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1));
        JLabel messageLabel = new JLabel("초기 아이디와 비밀번호는 admin입니다.");
        JLabel idLabel = new JLabel("아이디: ");
        idField = new JTextField();
        JLabel passwordLabel = new JLabel("비밀번호: ");
        passwordField = new JPasswordField();
        JButton loginButton = new JButton("로그인");
        //로그인 버튼
        loginButton.addActionListener(e -> {
            String id = idField.getText();
            String password = new String(passwordField.getPassword());
            //아이디와 비밀번호 확인 후 로그인 처리
            if (id.equals("admin") && password.equals("admin")) {
                JOptionPane.showMessageDialog(adminMenuFrame, "로그인 성공!");
                adminMenuFrame.dispose();
                showAdminMenuOptions();
            } else {
                JOptionPane.showMessageDialog(adminMenuFrame, "아이디 혹은 비밀번호가 일치하지 않습니다.");
            }
        });
        //컴포넌트 추가
        panel.add(messageLabel);
        panel.add(idLabel);
        panel.add(idField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);
        adminMenuFrame.add(panel);
        adminMenuFrame.setVisible(true);
        setVisible(false);
    } //////////////showAdminMenu

    private void showAdminMenuOptions() {
        //관리자 메뉴 옵션 프레임 생성
        adminMenuFrame = new JFrame("관리자 메뉴");
        adminMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminMenuFrame.setSize(400, 300);
        adminMenuFrame.setLocationRelativeTo(null);
        //패널 생성, 그리드 레이아웃 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9, 1));
        //관리자 메뉴 옵션 라벨 정의
        String[] adminMenuLabels = {"입력", "출력", "수정", "삭제", "검색", "파일저장", "음악재생", "음악중지", "메인메뉴"};
        adminMenuButtons = new JButton[adminMenuLabels.length];
        //관리자 메뉴 옵션 버튼 생성, 리스너 추가
        for (int i = 0; i < adminMenuLabels.length; i++) {
            adminMenuButtons[i] = new JButton(adminMenuLabels[i]);
            adminMenuButtons[i].addActionListener(this);
            panel.add(adminMenuButtons[i]);
        } //for
        //패널을 프레임에 추가, 화면 표시
        adminMenuFrame.add(panel);
        adminMenuFrame.setVisible(true);
        setVisible(false);
    } //////////////showAdminMenuOptions

    private void showUserMenu() {
        //사용자 메뉴 프레임 생성
        userMenuFrame = new JFrame("일반 사용자 메뉴");
        userMenuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        userMenuFrame.setSize(300, 200);
        userMenuFrame.setLocationRelativeTo(null);
        //사용자 메뉴 패널을 생성, 레이아웃 설정
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));
        //사용자 메뉴 라벨 정의
        String[] userMenuLabels = {"출력", "검색", "음악재생", "음악중지", "메인메뉴"};
        userMenuButtons = new JButton[userMenuLabels.length];
        //사용자 메뉴 버튼 생성
        for (int i = 0; i < userMenuLabels.length; i++) {
            userMenuButtons[i] = new JButton(userMenuLabels[i]);
            userMenuButtons[i].addActionListener(this);
            panel.add(userMenuButtons[i]);
        } //for
        //패널을 프레임에 추가, 화면 표시
        userMenuFrame.add(panel);
        userMenuFrame.setVisible(true);
        setVisible(false); 
    } //////////////showUserMenu

    private void printPerson(JFrame previousFrame) {
        //프레임 생성
        JFrame outputFrame = new JFrame("출력");
        outputFrame.setSize(300, 200);
        outputFrame.setLocationRelativeTo(null);
        outputFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //패널 생성, 레이아웃 설정
        JPanel panel = new JPanel(new GridLayout(4, 1));
        // 버튼 생성
        JButton nameButton = new JButton("이름");
        JButton ageButton = new JButton("나이");
        JButton phoneButton = new JButton("연락처");
        JButton backButton = new JButton("뒤로가기");
        //이름으로 정렬
        nameButton.addActionListener(e -> {
            displayByInitialConsonant();
            outputFrame.dispose();
            previousFrame.setVisible(true);
        });
        //나이, 연락처 정렬
        ageButton.addActionListener(e -> sortPeople("age", outputFrame, previousFrame));
        phoneButton.addActionListener(e -> sortPeople("phone", outputFrame, previousFrame));
        //뒤로 가기 버튼 추가
        backButton.addActionListener(e -> {
            outputFrame.dispose();
            previousFrame.setVisible(true);
        });
        //패널에 버튼 추가
        panel.add(nameButton);
        panel.add(ageButton);
        panel.add(phoneButton);
        panel.add(backButton);
        //패널을 프레임에 추가, 화면 표시
        outputFrame.add(panel);
        outputFrame.setVisible(true);
        previousFrame.setVisible(false);
    } //////////////printPerson


    private void sortPeople(String criteria, JFrame previousFrame, JFrame currentFrame) {
        //정렬 방법을 선택하기 위한 프레임 생성
        JFrame sortFrame = new JFrame("순서 선택");
        sortFrame.setSize(300, 200);
        sortFrame.setLocationRelativeTo(null);
        sortFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //정렬 방법을 담을 패널 생성, 레이아웃을 설정
        JPanel panel = new JPanel(new GridLayout(3, 1));
        //오름차순, 내림차순, 뒤로 가기 버튼 생성
        JButton ascendingButton = new JButton("오름차순");
        JButton descendingButton = new JButton("내림차순");
        JButton backButton = new JButton("뒤로가기");
        //오름차순 또는 내림차순 정렬
        ascendingButton.addActionListener(e -> {
        	sortFrame.dispose();
        	displaySorted(criteria, true, currentFrame);
        });
        descendingButton.addActionListener(e -> {
        	sortFrame.dispose();
        	displaySorted(criteria, false, currentFrame);
        });
        //뒤로 가기 버튼
        backButton.addActionListener(e -> {
            sortFrame.dispose();
            currentFrame.setVisible(true);
        });
        //패널 버튼
        panel.add(ascendingButton);
        panel.add(descendingButton);
        panel.add(backButton);
        sortFrame.add(panel);
        sortFrame.setVisible(true);
        previousFrame.setVisible(false);
    } //////////////sortPeople


    private void displaySorted(String criteria, boolean ascending, JFrame previousFrame) {
        //출력할 문자열을 저장할 변수
        StringBuilder output = new StringBuilder();
        //정렬 조건에 따라 출력할 메시지
        switch (criteria) {
            case "age":
                output.append(ascending ? "[나이를 오름차순으로 출력]\n" : "[나이를 내림차순으로 출력]\n");
                output.append(displaySortMenu(criteria, ascending));
                break;
            case "phone":
                output.append(ascending ? "[연락처를 오름차순으로 출력]\n" : "[연락처를 내림차순으로 출력]\n");
                output.append(displaySortMenu(criteria, ascending));
                break;
            default:
                throw new IllegalArgumentException("정렬이 이상해요. 정렬: " + criteria);
        } //switch
        //출력
        System.out.println(output);
        //이전 메뉴
        previousFrame.setVisible(true);
    } //////////////displaySorted

    private String displaySortMenu(String criteria, boolean ascending) {
        Comparator<People> comparator = null;
        switch (criteria) {
            case "name":
                comparator = Comparator.comparing(person -> person.name);
                break;
            case "age":
                comparator = Comparator.comparingInt(person -> person.age);
                break;
            case "phone":
                comparator = Comparator.comparing(person -> person.phone);
                break;
            default:
                throw new IllegalArgumentException("정렬이 이상해요. 정렬: " + criteria);
        }
        //내림차순일 경우
        if (!ascending) {
            comparator = comparator.reversed();
        }
        //정렬된 결과를 문자열로 반환
        List<People> sortedPeople = new ArrayList<>(people);
        sortedPeople.sort(comparator);
        StringBuilder output = new StringBuilder();
        for (People person : sortedPeople) {
            output.append(person).append("\n");
        } //for
        return output.toString();
    } //////////////displaySortMenu

    private void displayByInitialConsonant() {
        if (people.isEmpty()) {
            System.out.println("아무도 없습니다.");
            return;
        }
        //사람들을 초성별로 그룹화
        Map<Character, List<People>> InitialConsonantMap = new TreeMap<>();
        for (People person : people) {
            char InitialConsonant = getInitialConsonant(person.getName());
            if (InitialConsonant != '0') {
                InitialConsonantMap.computeIfAbsent(InitialConsonant, k -> new ArrayList<>()).add(person);
            }
        } //for
        //초성별로 정렬된 명단 출력
        for (Map.Entry<Character, List<People>> entry : InitialConsonantMap.entrySet()) {
            System.out.printf("[%c으로 시작하는 명단]%n", entry.getKey());
            for (People person : entry.getValue()) {
                System.out.println(person.toString());
            } //for
        } //for
    } //////////////displayByInitialConsonant

    private void inputPeople() {
        //이름, 나이, 주소, 전화번호를 입력 후 People 객체를 생성, 리스트에 추가
        String name = enterName();
        if (name == null) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        int age = enterAge();
        if (age == 0) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        String address = enterAddress();
        if (address == null) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        String phone = enterPhone();
        if (phone == null) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        people.add(new People(name, age, address, phone));
    } //////////////inputPeople

    private String enterName() {
        String inputName = null;
        while (true) {
            inputName = JOptionPane.showInputDialog(null, "이름을 입력하세요?");
            if (inputName == null) {
                break;
            } else if (inputName.matches("^[가-힣]+$")) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "한글 이름만 입력 가능합니다.");
            }
        } //while
        return inputName;
    } //////////////enterName 

    private int enterAge() {
        while (true) {
            String inputAge = JOptionPane.showInputDialog(null, "나이를 입력하세요?");
            if (inputAge == null) {
                break;
            }
            try {
                int age = Integer.parseInt(inputAge);
                return age;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "숫자만 입력 가능합니다.");
            }
        } //while
        return 0;
    } //////////////enterAge 

    private String enterAddress() {
        String address = null;
        while (true) {
            address = JOptionPane.showInputDialog(null, "사는곳을 입력하세요?");
            if (address == null) {
                break;
            }
            if (!address.trim().isEmpty()) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "주소를 입력하세요.");
            }
        } //while
        return address;
    } //////////////enterAddress 

    private String enterPhone() {
        String pattern = "\\d{3}-\\d{4}-\\d{4}";
        String inputPhone = null;
        while (true) {
            inputPhone = JOptionPane.showInputDialog(null, "연락처를 xxx-xxxx-xxxx 형식으로 입력하세요?");
            if (inputPhone == null) {
                break;
            }
            if (inputPhone.matches(pattern)) {
                break;
            } else {
                JOptionPane.showMessageDialog(null, "형식에 맞게 입력하세요.");
            }
        } //while
        return inputPhone;
    } //////////////enterPhone 

    private void editPeople() {
        String searchName = JOptionPane.showInputDialog(null, "수정할 사람의 이름을 입력하세요:", "수정", JOptionPane.QUESTION_MESSAGE);
        if (searchName == null) {
            return; //취소 버튼을 누르면 해당 메소드를 그냥 빠져나감
        } else if (searchName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "이름을 입력하지 않았습니다.", "에러", JOptionPane.ERROR_MESSAGE);
            return; //사용자가 값을 입력하지 않았을 때 에러 메시지를 띄우고 해당 메소드를 그냥 빠져나감
        }
        //검색하여 찾은 사람 정보 수정
        List<People> foundPeople = new ArrayList<>();
        for (People p : people) {
            if (p.name.equals(searchName)) {
                foundPeople.add(p);
            }
        } //for
        if (foundPeople.isEmpty()) {
            JOptionPane.showMessageDialog(null, searchName + "로(으로) 검색된 정보가 없어요.", "검색 결과", JOptionPane.INFORMATION_MESSAGE);
        } else if (foundPeople.size() == 1) {
            //이름 중복 x
        	editInputPerson(foundPeople.get(0));
        } else {
            //이름 중복 o
            String[] options = new String[foundPeople.size()];
            for (int i = 0; i < foundPeople.size(); i++) {
                options[i] = foundPeople.get(i).toString();
            } //for
            String selectedPerson = (String) JOptionPane.showInputDialog(null, "수정할 사용자를 선택하세요:", "중복 사용자", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selectedPerson != null) {
                for (People p : foundPeople) {
                    if (p.toString().equals(selectedPerson)) {
                    	editInputPerson(p);
                        break;
                    }
                } //for
            }
        }
    } //////////////editPeople
    
    private void editInputPerson(People person) {
        String name = editEnterName(person);
        if (name == null) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        int age = editEnterAge(person);
        if (age == 0) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        String address = editEnterAddress(person);
        if (address == null) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        String phone = editEnterPhone(person);
        if (phone == null) {
            JOptionPane.showMessageDialog(null, "입력이 취소되었습니다.");
            return;
        }
        JOptionPane.showMessageDialog(null, "사용자 정보가 성공적으로 수정되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
        person.name = name;
        person.age = age;
        person.address = address;
        person.phone = phone;
    } //////////////inputPeople
   
    private String editEnterName(People person) {
        //이름 수정
    	String newName = null;
    	while (true) {
    		newName = JOptionPane.showInputDialog(null, "새로운 이름을 입력하세요 (한글만 가능):", "이름 수정", JOptionPane.QUESTION_MESSAGE);
	        if (newName == null) {
	        	break;
	        } else if (!newName.trim().isEmpty() && newName.matches("^[가-힣]*$")) {
	        	break;
	        } else {
	            JOptionPane.showMessageDialog(null, "한글로만 입력하세요.", "에러", JOptionPane.ERROR_MESSAGE);
	        }
    	} //while
		return newName;
    } //////////////editInputPerson
    
    private int editEnterAge(People person) {
    	//나이 수정
        while (true) {
	        String newAgeString = JOptionPane.showInputDialog(null, "새로운 나이를 입력하세요 (숫자만 가능):", "나이 수정", JOptionPane.QUESTION_MESSAGE);
	        if (newAgeString == null) {
	        	break;
	        }
            try {
                int newAge = Integer.parseInt(newAgeString);
                return newAge;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "올바른 나이를 입력하세요.", "에러", JOptionPane.ERROR_MESSAGE);
            }
	    } //while
		return 0;
    } //////////////editEnterAge 
    
    private String editEnterAddress(People person) {
    	//주소 수정
    	String newAddress = null;
    	while (true) {
	        newAddress = JOptionPane.showInputDialog(null, "새로운 주소를 입력하세요:", "주소 수정", JOptionPane.QUESTION_MESSAGE);
	        if (newAddress == null) {
	            break;
	        }
	        if (!newAddress.trim().isEmpty()) {
                break;
	        } else {
	            JOptionPane.showMessageDialog(null, "주소를 입력하세요.", "에러", JOptionPane.ERROR_MESSAGE);
	        }
    	} //while
		return newAddress;
    } //////////////editEnterAddress
    
    private String editEnterPhone(People person) {
    	//연락처 수정
        String pattern = "\\d{3}-\\d{4}-\\d{4}";
        String newPhone = null;
        while (true) {
	        newPhone = JOptionPane.showInputDialog(null, "새로운 연락처를 입력하세요 (xxx-xxxx-xxxx 형식):", "연락처 수정", JOptionPane.QUESTION_MESSAGE);
	        if (newPhone == null) {
	        	break;
	        } else if (newPhone.matches(pattern)) {
	        	break;
	        } else {
	            JOptionPane.showMessageDialog(null, "올바른 형식으로 입력하세요 (xxx-xxxx-xxxx).", "에러", JOptionPane.ERROR_MESSAGE);
	        }
	    } //while
		return newPhone;
    } //////////////editEnterPhone

    private void deletePeople() {
        String searchName = JOptionPane.showInputDialog(null, "삭제할 사람의 이름을 입력하세요:", "삭제", JOptionPane.QUESTION_MESSAGE);
        if (searchName == null) {
            return; //취소 버튼을 누르면 해당 메소드를 그냥 빠져나감
        } else if (searchName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "이름을 입력하지 않았습니다.", "에러", JOptionPane.ERROR_MESSAGE);
            return; //사용자가 값을 입력하지 않았을 때 에러 메시지를 띄우고 해당 메소드를 그냥 빠져나감
        }
        //이름 입력 후 실행
        List<People> foundPeople = new ArrayList<>();
        for (People p : people) {
            if (p.name.equals(searchName)) {
                foundPeople.add(p);
            }
        } //for
        if (foundPeople.isEmpty()) {
            JOptionPane.showMessageDialog(null, searchName + "로(으로) 검색된 정보가 없어요.", "검색 결과", JOptionPane.INFORMATION_MESSAGE);
        } else if (foundPeople.size() == 1) {
            //이름 중복 x
            people.remove(foundPeople.get(0));
            JOptionPane.showMessageDialog(null, "사용자 정보가 성공적으로 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
        } else {
            //이름 중복 o
            String[] options = new String[foundPeople.size()];
            for (int i = 0; i < foundPeople.size(); i++) {
                options[i] = foundPeople.get(i).toString();
            } //for
            String selectedPerson = (String) JOptionPane.showInputDialog(null, "삭제할 사용자를 선택하세요:", "중복 사용자", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
            if (selectedPerson != null) {
                for (People p : foundPeople) {
                    if (p.toString().equals(selectedPerson)) {
                        people.remove(p);
                        JOptionPane.showMessageDialog(null, "사용자 정보가 성공적으로 삭제되었습니다.", "성공", JOptionPane.INFORMATION_MESSAGE);
                        break;
                    }
                } //for
            }
        }
    } //////////////deletePeople

    private void searchPeople() {
        String searchName = JOptionPane.showInputDialog(null, "검색할 사람의 이름을 입력하세요:", "검색", JOptionPane.QUESTION_MESSAGE);
        if (searchName == null) {
            return; //취소 버튼을 누르면 해당 메소드를 그냥 빠져나감
        } else if (searchName.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "이름을 입력하지 않았습니다.", "에러", JOptionPane.ERROR_MESSAGE);
            return; //사용자가 값을 입력하지 않았을 때 에러 메시지를 띄우고 해당 메소드를 그냥 빠져나감
        }
        StringBuilder searchResult = new StringBuilder();
        boolean found = false;
        for (People p : people) {
            if (p.name.equals(searchName)) {
                if (!found) {
                    searchResult.append(String.format("[%s로(으로) 검색한 결과]\n", searchName));
                    found = true;
                }
                searchResult.append(p.get()).append("\n");
            }
        } //for
        if (!found) {
            searchResult.append(searchName).append("로(으로) 검색된 정보가 없어요.");
        }
        JOptionPane.showMessageDialog(null, searchResult.toString(), "검색 결과", JOptionPane.INFORMATION_MESSAGE);
    } //////////////searchPeople

    private void savePeople() {
		if(people.isEmpty()) {
			System.out.println("파일로 저장할 명단이 없어요.");
			return;
		}else {
			ObjectOutputStream out = null;
			try {
				out = new ObjectOutputStream((new FileOutputStream("src/console/academy/People.txt")));
				out.writeObject(people);
				System.out.println("파일이 저장되었습니다.");
			} catch(IOException e) {
				System.out.println("파일 저장 시 오류: "+e.getMessage());
			} finally {
				try {
					if(out != null) {
						out.close();
					}
				} catch(Exception e){
					
				}
			}
		}
	} //////////////savePeople
    
	private void loadPeople() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream("src/console/academy/People.txt"));
			people = (List<People>)ois.readObject();
		} catch(Exception e) {
			System.out.println("파일이 없어요");
		}
		  finally {
			try   {
				if(ois != null) {
					ois.close();
				}
			} catch (IOException e) {
				System.out.println("스트림 닫기 시 오류!");
			}
		}
	} //////////////loadPeople
    
	public static char getInitialConsonant(String value) {
		if(!Pattern.matches("^[가-힣]{2,}$", value.trim())) return '0';
		char lastName=value.charAt(0);
		
		int index = (lastName-'가')/28/21; //초성의 인덱스 얻기
		char[] initialConsonants= {'ㄱ','ㄲ','ㄴ','ㄷ','ㄸ','ㄹ','ㅁ','ㅂ','ㅃ','ㅅ','ㅆ','ㅇ','ㅈ','ㅉ','ㅊ','ㅋ','ㅌ','ㅍ','ㅎ'};
		return initialConsonants[index];
	} //////////////getInitialConsonant
} ////class
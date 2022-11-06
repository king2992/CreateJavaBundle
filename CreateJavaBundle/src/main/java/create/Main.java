package create;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class Main {
  static Path currentPath = Paths.get("");
  static String path = currentPath.toAbsolutePath().toString() + "\\CreateJavaBundle\\src\\main\\java\\";
  public static void main(final String[] args) throws IOException {

    String domainName;
    String instanceName;
    String auth;
    String domainKorName;
    String tableName;

    Scanner sc = new Scanner(System.in);
    System.out.println("권한을 입력하세요");
    auth = sc.next();
    System.out.println("도메인 이름을 입력하세요");
    domainName = sc.next();
    System.out.println("도메인 한글 이름을 입력하세요");
    domainKorName = sc.next();
    System.out.println("인스턴스 이름을 입력하세요");
    instanceName = sc.next();

    tableName = domainName.toUpperCase(Locale.ROOT);

    List<String> list = getTemplates();
    /**
     * 템플릿 파일을 순서대로 읽어서 변환한다.
     */
    for(int i = 0; i < list.size(); i++){
      String contents = "";

      BufferedReader reader = new BufferedReader(
              new FileReader(path + "template\\"+list.get(i))
      );
      String str;
      while ((str = reader.readLine()) != null) {
        contents += str;
        contents += System.lineSeparator();
      }
      contents = contents.replace("${auth}", auth);
      contents = contents.replace("${DomainName}", domainName);
      contents = contents.replace("${DomainKorName}", domainKorName);
      contents = contents.replace("${InstanceName}", instanceName);
      contents = contents.replace("${TableName}", tableName);
      reader.close();
      createTemplate(domainName, contents, i);
    }
  }


  public static List getTemplates(){

    List<String> list = new ArrayList<>();
    list.add("ControllerTemplate.txt");
    list.add("DomainTemplate.txt");
    list.add("Mapper_SQLTemplate.txt");
    list.add("MapperTemplate.txt");
    list.add("RepositoryTemplate.txt");
    list.add("ResponseDtoTemplate.txt");
    list.add("SaveDtoTemplate.txt");
    list.add("SearchDtoTemplate.txt");
    list.add("ServiceTemplate.txt");
    list.add("ApiTemplate.txt");

    return list;
  }

  public static List setResultFileName(String domainName){

    List<String> list = new ArrayList<>();
    list.add(domainName+"Controller.java");
    list.add(domainName+".java");
    list.add(domainName+"_SQL.xml");
    list.add(domainName+"Mapper.java");
    list.add(domainName+"Repository.java");
    list.add(domainName+"ResponseDto.java");
    list.add(domainName+"SaveDto.java");
    list.add(domainName+"SearchDto.java");
    list.add(domainName+"Service.java");
    list.add(domainName+"API.js");

    return list;
  }

  public static void createTemplate(String domainName, String contents, int i) throws IOException {
    List<String> list = setResultFileName(domainName);

    File folder = new File(path +  domainName);
    if (!folder.exists()) {
      folder.mkdir();
    }

    File file = new File(path + domainName + "\\" +list.get(i));
    if(!file.exists()){
      file.createNewFile();
    }
    BufferedWriter writer = new BufferedWriter(new FileWriter(file, true));
    writer.write(contents);
    writer.flush();
    writer.close();
    System.out.println(path + domainName + "\\" +list.get(i) + "파일이 생성되었습니다.");
  }
}

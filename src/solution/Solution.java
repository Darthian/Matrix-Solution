package solution;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Solution {

  static Long[][][] matrix;

  public static void main(String[] args) throws FileNotFoundException {
    // Scanner scan = new Scanner(System.in);
    Scanner scan = new Scanner(new FileInputStream("src/solution/input"));

    List<String> patterns = Arrays.asList("(^(\\d+(\\d+)*)$)", "^((\\d+(\\d+)*) (\\d+(\\d+)*)$)",
        "((UPDATE) ((\\d+(\\d+)*) (\\d+(\\d+)*) (\\d+(\\d+)*)) (\\d+(\\d+)*))",
        "((QUERY) ((\\d+(\\d+)*) (\\d+(\\d+)*) (\\d+(\\d+)*)) ((\\d+(\\d+)*) (\\d+(\\d+)*) (\\d+(\\d+)*)))");

    while (scan.hasNext()) {
      MatcherInfo matcherInfo = getMatch(scan.nextLine(), patterns, 0);
      switch (matcherInfo.index) {
        case 0:
          break;
        case 1:
          int N = Integer.valueOf(matcherInfo.matcher.group(2));
          matrix = new Long[N][N][N];
          for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
              for (int k = 0; k < N; k++)
                matrix[i][j][k] = 0L;
          break;
        case 2:
          updateMatrix(matrix, matcherInfo.matcher);
          break;
        case 3:
          queryMatrix(matrix, matcherInfo.matcher);
          break;
      }
    }
    scan.close();
  }

  private static MatcherInfo getMatch(String line, List<String> patterns, int index) {
    Pattern patterIndex = Pattern.compile(patterns.get(index));
    Matcher matcher = patterIndex.matcher(line);
    if (matcher.find()) {
      return new MatcherInfo(index, matcher);
    } else {
      return getMatch(line, patterns, index + 1);
    }
  }

  private static void updateMatrix(Long[][][] matrix, Matcher matcher) {
    matrix[Integer.valueOf(matcher.group(4)) - 1][Integer.valueOf(matcher.group(6)) - 1][Integer
        .valueOf(matcher.group(8)) - 1] = Long.valueOf(matcher.group(10));
  }

  private static void queryMatrix(Long[][][] matrix, Matcher matcher) {
    int x1 = Integer.valueOf(matcher.group(4)) - 1;
    int x2 = Integer.valueOf(matcher.group(11)) - 1;
    int y1 = Integer.valueOf(matcher.group(6)) - 1;
    int y2 = Integer.valueOf(matcher.group(13)) - 1;
    int z1 = Integer.valueOf(matcher.group(8)) - 1;
    int z2 = Integer.valueOf(matcher.group(15)) - 1;
    Long sum = 0L;
    for (int i = x1; i <= x2; i++)
      for (int j = y1; j <= y2; j++)
        for (int k = z1; k <= z2; k++)
          sum = sum + matrix[i][j][k];
    System.out.println(sum);
  }

  public static class MatcherInfo {
    public Integer index;
    public Matcher matcher;

    public MatcherInfo(Integer index, Matcher matcher) {
      this.index = index;
      this.matcher = matcher;
    }
  }

}

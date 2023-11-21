package com.example.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
public class HW {

	// 分糖果
	// public static void main(String[] args) {
	// 	Scanner in = new Scanner(System.in);
	// 	int num = in.nextInt();
	// 	in.close();
	//
	// 	int ans = 0;
	// 	while (num != 1) {
	// 		if ((num & 1) == 1) {
	// 			if (needPlus(num)) {
	// 				num++;
	// 			} else {
	// 				num--;
	// 			}
	// 			ans++;
	// 		} else {
	// 			num /= 2;
	// 			ans++;
	// 		}
	// 	}
	// 	System.out.println(ans);
	// }
	//
	// private static boolean needPlus(int num) {
	// 	int p = 0;
	// 	while (num / 2 != 0) {
	// 		p++;
	// 		num /= 2;
	// 	}
	// 	int sum = 0;
	// 	boolean needPlus = false;
	// 	for (int i = 0; i < p; ++i) {
	// 		sum += Math.pow(2, i);
	// 		int m = (int) Math.pow(2, p + 1);
	// 		int d = (int) Math.pow(2, p) + sum;
	// 		if (Math.abs(m - num) < Math.abs(d - num)) {
	// 			needPlus = m > num;
	// 		} else {
	// 			needPlus = d > num;
	// 		}
	// 	}
	// 	return needPlus;
	// }

	// 数据分类
	// public static void main(String[] args) throws IOException {
	// 	BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
	// 	String line = br.readLine();
	// 	br.close();
	//
	// 	String[] nums = line.split(" ");
	// 	int c = Integer.parseInt(nums[0]);
	// 	int b = Integer.parseInt(nums[1]);
	//
	// 	int[] r = new int[c];
	// 	for (int i = 2; i < nums.length; ++i) {
	// 		int n = Integer.parseInt(nums[i]);
	// 		int m = (n >> 24 & 0xff) + (n >> 16 & 0xff) + (n >> 8 & 0xff) + (n & 0xff) % b;
	// 		if (m < c) {
	// 			r[m]++;
	// 		}
	// 	}
	//
	// 	int ans = 0;
	// 	for (int i : r) {
	// 		ans = Math.max(ans, i);
	// 	}
	// 	System.out.println(ans);
	// }

	// 目录删除
	// public static void main(String[] args) throws IOException {
	// 	BufferedReader br =new BufferedReader(new InputStreamReader(System.in));
	// 	int count = Integer.parseInt(br.readLine());
	// 	String[][] childFatherIds = new String[count][2];
	// 	for (int i = 0; i < count; ++i) {
	// 		String ids = br.readLine();
	// 		String[] idCF = ids.split(" ");
	// 		childFatherIds[i] = idCF;
	// 	}
	// 	String deleteId = br.readLine();
	// 	br.close();
	//
	//
	// 	List<String> deleteIds = new ArrayList<>();
	// 	Set<String> allIds = new HashSet<>();
	// 	deleteIds.add(deleteId);
	// 	for (String[] arr : childFatherIds) {
	// 		allIds.add(arr[0]);
	// 		allIds.add(arr[1]);
	// 		if (deleteIds.contains(arr[1])) {
	// 			deleteIds.add(arr[0]);
	// 		}
	// 	}
	//
	// 	boolean hasLeft = true;
	// 	while (hasLeft) {
	// 		hasLeft = false;
	// 		for (String[] arr : childFatherIds) {
	// 			if (deleteIds.contains(arr[1]) && !deleteIds.contains(arr[0])) {
	// 				deleteIds.add(arr[0]);
	// 				hasLeft = true;
	// 			}
	// 		}
	// 	}
	//
	// 	deleteIds.forEach(allIds::remove);
	//
	// 	List<String> ans = new ArrayList<>(allIds);
	// 	ans.sort(Comparator.comparingInt(Integer::parseInt));
	// 	for (String id : ans) {
	// 		if ("0".equals(id)) {
	// 			continue;
	// 		}
	// 		System.out.print(id + " ");
	// 	}
	// 	System.out.println();
	// }


// import java.util.List;某停车场有一批停车记录，需要构建一个方法 getTopCars ，获取指定月份 中，总停车时长最长的5台车的车牌号，按停车总时长从高往低输出。如果停车总时长相同，则按照停车次数从高到低输入。如果停车次数也相同，则按照车牌号 排序（字符串排序），从高到低输出。如不满5辆车，则按照实际的数量输出
// 		 停车记录结构为 name = 车牌号，date = 停车日期 ，time = 单次停车时长
// 		 用例1：入参为：month = 08，records = [{T9000,2021-08-01,15},
// 		 {T9000,2021-08-01,40},
// 		 {T8000,2021-08-01,50},
// 		 {T7000,2021-06-01,120}]
// 		 排除2021-06-01 的记录非8月份的，汇总计算后，得到  T9000 55min, T8000 50min ,所以输出结果为[T9000,T8000]
// 		 用例2:
// 		 入参为：month = 12，records = [{T9000, 2011-12-23, 10}
// 		 {T9000, 2011-12-23, 90}
// 		 {T8000, 2011-12-23, 1}
// 		 {T7001, 2011-12-23, 10}
// 		 {T7002, 2011-12-23, 5}
// 		 {T7002, 2011-12-23, 5}
// 		 {T8000, 2011-12-23, 99}
// 		 {T2001, 2011-12-23, 9}
// 		 {T2003, 2011-12-23, 9}
// 		 {T1000, 2011-08-23, 120}]
// 		 最后输出结果为[T9000,T8000,T7002,T7001,T2003]
//
//
// 		 public class TopCar{
// 	static class Record {
// 		String name;
// 		String date;
// 		int time;
// Record(String name, String date, int time) {
// 			this.name = name;
// 			this.date = date;
// 			this.time = time;
// 		}
// 	}
// 	//   待实现函数，在此函数中填入答题代码
// 	private static List<String> getTopCars(int month, List<Record> records) {
//
// 	}
// 	public static void main(String[] args) {
//    ….
// 		List<String> r = getTopCars(xxx, param);
// 		for(String result : r){
// 			System.out.println(result);
// 		}
// 	}
// }


	public static void main(String[] args) {
		List<Record> list = new ArrayList<>();
		list.add(new Record("T9000", "2011-12-23", 10));
		list.add(new Record("T9000", "2011-12-23", 90));
		list.add(new Record("T8000", "2011-12-23", 1));
		list.add(new Record("T7001", "2011-12-23", 10));
		list.add(new Record("T7002", "2011-12-23", 5));
		list.add(new Record("T7002", "2011-12-23", 5));
		list.add(new Record("T8000", "2011-12-23", 99));
		list.add(new Record("T2001", "2011-12-23", 9));
		list.add(new Record("T2003", "2011-12-23", 9));
		list.add(new Record("T1000", "2011-08-23", 120));

		int month = 12;
		System.out.println(getTopCars(month, list));

	}

	@Getter
	static class Record {
		String name;
		String date;
		int time;

		Record(String name, String date, int time) {
			this.name = name;
			this.date = date;
			this.time = time;
		}
	}

	@Data
	@NoArgsConstructor
	@AllArgsConstructor
	static class RecordWrapper implements Comparable<RecordWrapper> {
		String name;
		int totalTime;
		int count;

		public RecordWrapper(Record record) {
			this.name = record.getName();
			totalTime = record.getTime();
			count = 1;
		}

		@Override
		public int compareTo(RecordWrapper o) {
			if (this.getTotalTime() != o.getTotalTime()) {
				return o.getTotalTime() - this.getTotalTime();
			}
			if (this.getCount() != o.getCount()) {
				return o.getCount() - this.getCount();
			}
			return o.getName().compareTo(this.getName());
		}
	}

	// 待实现函数，在此函数中填入答题代码
	private static List<String> getTopCars(int month, List<Record> records) {
		if (records == null || records.isEmpty()) {
			return new ArrayList<>();
		}

		// 月份到 对象（车牌及停车时长、次数 ）的排序集合 的映射
		Map<String, Set<RecordWrapper>> month2WrappersMap = new HashMap<>();
		for (Record r : records) {
			String m = r.getDate().split("-")[1];
			String name = r.getName();
			// 指定月份的 对象（车牌及停车时长、次数 ）的排序集合
			Set<RecordWrapper> wrappers = month2WrappersMap.getOrDefault(m, new TreeSet<>());
			boolean contained = false;
			Iterator<RecordWrapper> iterator = wrappers.iterator();
			RecordWrapper wrapper = null;
			while (iterator.hasNext()) {
				wrapper = iterator.next();
				if (wrapper.getName().equals(name)) {
					iterator.remove();
					wrapper.setTotalTime(wrapper.getTotalTime() + r.getTime());
					wrapper.setCount(wrapper.getCount() + 1);
					contained = true;
					break;
				}
			}
			// 包含当前车牌号时，注意这里要重新往 wrappers 里面加，如果仅仅只是在 while 循环里修改当前 wrapper 的停车时长和次数，该 wrapper 在 wrappers 中的顺序不会调整
			if (contained) {
				wrappers.add(wrapper);
			}
			// 不包含当前车牌号时，添加新对象，该对象的停车次数为 1
			if (!contained) {
				wrappers.add(new RecordWrapper(r));
			}
			month2WrappersMap.put(m, wrappers);
		}

		List<String> answer = new ArrayList<>();
		String monthString = month < 10 ? "0" + month : "" + month;
		Set<RecordWrapper> set = month2WrappersMap.get(monthString);
		int i = 0;
		for (RecordWrapper r : set) {
			if (i++ < 5) {
				answer.add(r.getName());
			}
		}
		return answer;
	}


	// 最长回文子串
	// public static void main(String[] args) {
	// 	Scanner scanner = new Scanner(System.in);
	// 	String input = scanner.nextLine();
	// 	// String input = UUID.randomUUID().toString().replaceAll("-", "");
	// 	System.out.println(input);
	// 	if (input == null) {
	// 		return;
	// 	}
	//
	// 	System.out.println(longestPalindromes(input));
	// }
	//
	// private static String longestPalindromes(String input) {
	// 	int len = input.length();
	// 	boolean[][] dp = new boolean[len][len];
	//
	// 	for (int i = 0; i < len; ++i) {
	// 		dp[i][i] = true;
	// 	}
	//
	// 	char[] chars = input.toCharArray();
	// 	int f = 0, t = 0;
	// 	// 注意这里的遍历顺序，先固定索引之间的差值，再将开始索引 i 往后移动，直到 结束索引 j 为 输入串 input 的最大索引，每移动一次判断 dp[i][j] 是否为回文串
	// 	// 然后再将索引差值加 1，同样的方法将 i 往后移动一步判断一次 dp[i][j]，这样能保证取 dp[i+1][j-1] 时，dp[i+1][j-1]  是判断过后的结果
	// 	// 如果循环条件是  for (int i = 0; i < len - 1; ++i) { for (int j = i + 1; j < len; ++j) {
	// 	//  那么 dp[i][j] 取 dp[i+1][j-1] 时，但 dp[i+1][j-1] 还没有经过判断，就会取到默认值 false
	// 	for (int l = 1; l < len - 1; ++l) {
	// 		for (int i = 0; i < len - 1; ++i) {
	// 			int j = i + l;
	// 			if (j >= len) {
	// 				break;
	// 			}
	//
	// 			if (chars[i] == chars[j]) {
	// 				if (j == i + 1 || j == i + 2) {
	// 					dp[i][j] = true;
	// 				} else {
	// 					dp[i][j] = dp[i + 1][j - 1];
	// 				}
	// 			} else {
	// 				dp[i][j] = false;
	// 			}
	//
	// 			if (dp[i][j] && j - i > t - f) {
	// 				f = i;
	// 				t = j;
	// 			}
	// 		}
	// 	}
	//
	// 	return input.substring(f, t + 1);
	// }

}

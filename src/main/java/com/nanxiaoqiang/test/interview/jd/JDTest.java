package com.nanxiaoqiang.test.interview.jd;

/**
 * 京东面试题
 * 
 * <pre>
 * 1
 * 2 3
 * 4 6 5
 * 7 9 8 10
 * 15 14 13 12 11
 * 求从左上走到右下，权重最高的路径。
 * 每次只能向下或向右下走。
 * </pre>
 * 
 * @author nanxiaoqiang
 * 
 * @version 2016年1月9日
 */
public class JDTest {

	public JDTest() {
	}

	public static void main(String[] args) {

	}

	private void findMaxPath(int[][] matrix) {
		int m = matrix.length;
		int n = matrix[0].length;
		int[] dp = new int[m + 1];
		for (int i = 0; i < m; i++)
			for (int j = 0; j <= i; j++) {
				dp[i + 1] = Math.max(dp[i] + matrix[i][j], dp[i + 1]
						+ matrix[i][j + 1]);
			}

	}
}

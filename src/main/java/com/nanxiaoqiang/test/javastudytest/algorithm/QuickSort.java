package com.nanxiaoqiang.test.javastudytest.algorithm;

import org.apache.commons.lang3.RandomUtils;

/**
 * 快速排序<br/>
 * 把整个序列看做一个数组，把第零个位置看做中轴，和最后一个比，如果比它小交换，比它大不做任何处理；交换了以后再和小的那端比，比它小不交换，比他大交换。
 * 这样循环往复，一趟排序完成，左边就是比中轴小的，右边就是比中轴大的，然后再用分治法，分别对这两个独立的数组进行排序。
 * 
 * @author nanxiaoqiang
 * 
 * @version 0.1
 * 
 * @since 2014年9月21日
 * 
 */
public class QuickSort {

	int[] sort_nums = { 52, 21, 45, 95, 93, 24, 97, 39, 90, 56, 93, 74, 55, 71,
			73, 24, 33, 89, 86, 46, 85, 99, 27, 38, 68, 48, 18, 33, 27, 5, 40,
			66, 78, 41, 87, 74, 29, 18, 77, 11, 70, 19, 73, 59, 50, 59, 30, 65,
			52, 19, 48, 73, 87, 8, 43, 1, 5, 0, 62, 40, 5, 19, 61, 97, 87, 33,
			36, 94, 99, 67, 81, 94, 58, 88, 22, 9, 56, 19, 71, 46, 59, 31, 50,
			3, 66, 74, 20, 21, 80, 67, 97, 87, 52, 43, 81, 26, 69, 67, 34, 63 };

	public QuickSort() {
	}

	public void startQuickSort(int[] nums) {
		if (nums.length > 0) {
			quickSort(nums, 0, nums.length - 1);
		} else {
			System.out.println("数组长度不够！");
		}
	}

	/**
	 * 快速排序
	 * 
	 * @param nums
	 * @param i
	 * @param length
	 */
	private void quickSort(int[] nums, int low, int high) {
		if (low < high) {
			int middle = getMiddle(nums, low, high);

			quickSort(nums, low, middle - 1);

			quickSort(nums, middle + 1, high);
		}
	}

	/**
	 * 将数组二分
	 * 
	 * @param nums
	 * @param low
	 * @param high
	 * @return
	 */
	private int getMiddle(int[] nums, int low, int high) {
		// 第一次：以最左，即第0个为中轴
		int tmp = nums[low];// 将第low个作为中轴

		while (low < high) {
			// 第一次：比较第0个和最后一个的大小，
			// 如果右边的比左边的大则跳过，
			// 知道出现左边的比右边的大为止。
			while (low < high && nums[high] >= tmp) {
				high--;// 用第low个和第high个作比较，如果右边比左边大，那么就跳过。
			}
			// 第一次：把第0个改变为较小的那个数字174521 254312
			nums[low] = nums[high];
			while (low < high && nums[low] <= tmp) {
				low++;// 第一次：小的放到了第一个，然后继续比较第1（0+1）个，如果比比较的大数小，low则继续又移动
			}
			nums[high] = nums[low];
		}
		nums[low] = tmp;
		return low;
	}

	public static void main(String[] args) {
		QuickSort q = new QuickSort();
		// 测试先生成100个随机数使用
		for (int i = 0; i < 100; i++) {
			int ramnum = RandomUtils.nextInt(0, 100);
			q.sort_nums[i] = ramnum;
			System.out.print(ramnum);
			System.out.print(',');
		}
		System.out.print("\r\n");
		System.out.println("**************************");
		q.startQuickSort(q.sort_nums);// 排序
		for (int i = 0; i < q.sort_nums.length; i++) {
			System.out.println(q.sort_nums[i]);
		}
	}

}

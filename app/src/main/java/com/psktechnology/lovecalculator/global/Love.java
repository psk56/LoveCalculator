package com.psktechnology.lovecalculator.global;

import java.util.Vector;

import android.annotation.SuppressLint;

@SuppressLint("DefaultLocale")
public class Love {
	
	private String name1;
	private String name2;

	public void setFirstName(String name1) {
		this.name1 = name1.toUpperCase();
	}

	public void setSecondName(String name2) {
		this.name2 = name2.toUpperCase();
	}

	public void setNames(String name1, String name2) {
		this.name1 = name1.toUpperCase();
		this.name2 = name2.toUpperCase();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	private Vector getCount() {
		Vector list = new Vector();
		String love = "LOVES";
		String name1 = this.name1;
		String name2 = this.name2;
		for (int i = 0; i < name1.length(); i++) {
			String temp = name1.charAt(i) + "";

			if (list.contains(temp)) {
				int indexOfElement = list.indexOf(temp);
				int prevCount = Integer.parseInt(list.get(++indexOfElement).toString());
				prevCount++;
				String newCount = (prevCount) + "";
				list.set(indexOfElement, newCount);
				continue;
			}

			list.add(temp);
			list.add("1");
		}

		for (int i = 0; i < name2.length(); i++) {
			String temp = name2.charAt(i) + "";

			if (list.contains(temp)) {
				int indexOfElement = list.indexOf(temp);
				int prevCount = Integer.parseInt(list.get(++indexOfElement).toString());
				prevCount++;
				String newCount = (prevCount) + "";
				list.set(indexOfElement, newCount);
				continue;
			}

			list.add(temp);
			list.add("1");
		}

		for (int i = 0; i < love.length(); i++) {
			String temp = love.charAt(i) + "";

			if (list.contains(temp)) {
				int indexOfElement = list.indexOf(temp);
				int prevCount = Integer.parseInt(list.get(++indexOfElement).toString());
				prevCount++;
				String newCount = (prevCount) + "";
				list.set(indexOfElement, newCount);
				continue;
			}

			list.add(temp);
			list.add("1");
		}

		Vector result = new Vector();

		for (int i = 1; i < list.size(); i += 2) {
			result.add(list.get(i));
		}

		// System.out.println( result );
		return result;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public int getLovePer() {
		Vector count = getCount();
		if (count.size() == 1) {
			String result = count.get(0).toString() + "";
			return Integer.parseInt(result);
		}

		if (count.size() == 2) {
			String result = count.get(0).toString() + count.get(1).toString();
			return Integer.parseInt(result);
		}

		do {
			Vector sub = new Vector();
			int size = count.size() / 2;
			// System.out.println( count.size() / 2 );
			for (int i = 0; i < size; i++) {
				String newC = (Integer.parseInt(count.get(i).toString()) + Integer.parseInt(count.get(count.size() - 1 - i).toString())) + "";

				if (newC.length() == 2) {
					sub.add((newC.charAt(0) + ""));
					sub.add((newC.charAt(1) + ""));
				} else {
					sub.add(newC);
				}
			}

			if ((size * 2) != count.size())
				sub.add(count.get(size));

			count = new Vector();
			count = sub;
			// System.out.println( count );
		} while (count.size() != 2);

		String result = count.get(0).toString() + count.get(1).toString();
		return Integer.parseInt(result);
	}

	public int getLovePer(String name1, String name2) {
		String temp1 = this.name1;
		String temp2 = this.name2;

		setNames(name1, name2);
		int lPercentage = getLovePer();
		setNames(temp1, temp2);

		return lPercentage;
	}

	public String toString() {
		String result = String.valueOf(getLovePer());
		return result;
	}

}
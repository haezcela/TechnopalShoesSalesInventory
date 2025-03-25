package com.laponhcet.test;

import com.mytechnopal.util.QRCodeUtil;

public class Test {
	public static void main(String[] a) {
		System.out.println(QRCodeUtil.generateQRCodeBase64("0168324", 200, 300));
	}
}

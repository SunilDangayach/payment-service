package com.discover.dpay.payment_service.data.jpa.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DatabindException;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

public class QRcodeGenerator {

	public static void main(String args[]) throws JsonProcessingException, DatabindException, IOException, WriterException {
		InputStream is = QRcodeGenerator.class.getResourceAsStream("/verification.json");
		String result = new BufferedReader(new InputStreamReader(is))
				   .lines().collect(Collectors.joining("\n"));
		QRCodeWriter qrCodeWriter = new QRCodeWriter();
		BitMatrix bitMatrix = qrCodeWriter.encode(result, BarcodeFormat.QR_CODE, 500, 500);
        ImageIO.write(MatrixToImageWriter.toBufferedImage(bitMatrix), "png", new File("C:\\Test\\output.png"));
		System.out.print(MatrixToImageWriter.toBufferedImage(bitMatrix));
	}
}

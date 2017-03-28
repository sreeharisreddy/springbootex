package com.sree.share.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sree.ShareConfig;
import com.sree.share.entity.BSETradeMaster;

@Component
public class BSEGipReader {

	@Autowired
	ShareConfig config;

	private static final Logger logger = LoggerFactory.getLogger(BSEGipReader.class);

	public List<BSETradeMaster> download(Date d) throws Exception {
		try {
			logger.info("Loading the BSE file ");
			load(d);
			logger.info("Unzipping the file BSE file ");
			String file = unZipIt();
			logger.info("reading the csv file {}", file);
			return readFromCSV(file);
		} catch (IOException e) {
			throw e;
		}
	}

	private List<BSETradeMaster> readFromCSV(String path) throws Exception {
		List<BSETradeMaster> lis = new ArrayList<>();
		try {
			BufferedReader bf = new BufferedReader(new FileReader(new File(path)));
			String ln = "";
			ln = bf.readLine();
			ln = bf.readLine();
			while (ln != null) {

				BSETradeMaster bseTradeMaster = new BSETradeMaster();
				String[] split = ln.split(",");
				// SC_CODE SC_NAME SC_GROUP SC_TYPE OPEN HIGH LOW CLOSE LAST
				// PREVCLOSE NO_TRADES NO_OF_SHRS NET_TURNOV
				if (split.length > 5) {
					bseTradeMaster.setShareId(Long.valueOf(split[0]));
					bseTradeMaster.setSname(split[1]);
					bseTradeMaster.setScgroup(split[2]);
					bseTradeMaster.setStype(split[3]);
					bseTradeMaster.setOpen(Double.valueOf(split[4]));
					bseTradeMaster.setHigh(Double.valueOf(split[5]));
					bseTradeMaster.setLow(Double.valueOf(split[6]));
					bseTradeMaster.setCurrent_price(Double.valueOf(split[7])); // close
					bseTradeMaster.setLast(Double.valueOf(split[8]));
					bseTradeMaster.setPrev_pric(Double.valueOf(split[9]));
					bseTradeMaster.setNoOfTrades(Long.valueOf(split[10]));
					bseTradeMaster.setNoOfShares(Long.valueOf(split[11]));
					bseTradeMaster.setNetTurnover(BigDecimal.valueOf(Double.valueOf(split[12])));
					lis.add(bseTradeMaster);
				}
				ln = bf.readLine();
			}
			System.out.println("records  sizw" + lis.size());
			if (lis.size() == 3202) {
				System.out.println();
			}

			return lis;
		} catch (FileNotFoundException e) {
			throw e;
		} catch (Exception e) {
			throw e;
		}
	}

	public String unZipIt() throws Exception {

		byte[] buffer = new byte[1024];
		try {
			// create output directory is not exists
			String downloadedPath = config.getBseDownloadpath();
			File folder = new File(downloadedPath);
			if (!folder.exists()) {
				folder.mkdir();
			}
			String zipFile = downloadedPath + "/download.zip";
			ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
			ZipEntry ze = zis.getNextEntry();
			while (ze != null) {
				String fileName = ze.getName();
				File newFile = new File(downloadedPath + File.separator + fileName);
				System.out.println("file unzip : " + newFile.getAbsoluteFile());
				new File(newFile.getParent()).mkdirs();
				FileOutputStream fos = new FileOutputStream(newFile);
				int len;
				while ((len = zis.read(buffer)) > 0) {
					fos.write(buffer, 0, len);
				}

				fos.close();
				ze = zis.getNextEntry();
				return newFile.getAbsoluteFile().getPath();
			}

			zis.closeEntry();
			zis.close();

			System.out.println("Done");
		} catch (Exception e) {
			throw e;
		}
		return null;
	}

	public void load(Date d) throws Exception {
		int times = 1;
		int maxTimes = 10;
				while(true){
					if(times<=maxTimes){
						logger.info("Trying to load from BASE Again {}",times);
						try {
							loadFile(d);
							break;
						} catch (Exception e) {
							Thread.sleep(1000*5*times);
							times++;
							e.printStackTrace();
						}
				}else {
					logger.info("Not able to load for "+d);
					throw new Exception("Not able to load for "+d);
				}
			}
	}
	
	public void loadFile(Date d) throws Exception{

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyy");
		String format = sdf.format(d);
		logger.info("loading from http://www.bseindia.com/download/BhavCopy/Equity/EQ" + format + "_CSV.ZIP");
		URL url = new URL("http://www.bseindia.com/download/BhavCopy/Equity/EQ" + format + "_CSV.ZIP");
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		InputStream in = connection.getInputStream();
		FileOutputStream out = new FileOutputStream(config.getBseDownloadpath().trim() + "/download.zip");
		copy(in, out, 1024);
		out.close();
	
	}

	public static void copy(InputStream input, OutputStream output, int bufferSize) throws IOException {
		byte[] buf = new byte[bufferSize];
		int n = input.read(buf);
		while (n >= 0) {
			output.write(buf, 0, n);
			n = input.read(buf);
		}
		output.flush();
	}
}

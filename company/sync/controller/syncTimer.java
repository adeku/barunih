package verse.sync.controller;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class syncTimer {
	Timer timerIT;
	String variableSync;
	public Date dtNowTimer = new Date();

	public void runSyncronize(final String urlSEnd, final String urlSErver,
			final int computer_id, final String userID, final String companyID) {
		timerIT = new Timer();
		timerIT.schedule(new TimerTask() {

			public void run() {
				variableSync = "";
				Date timeSNoW = new Date();
				System.out.println("dtNowTimer = " + timeSNoW.toString());
				System.out.println("dt next = " + dtNowTimer.toString());
				long diff = timeSNoW.getTime() - dtNowTimer.getTime();
				double diffInMinutes = diff / ((double) 1000 * 60);
				System.out.println("range = " + diffInMinutes);
				if (diffInMinutes >= 3) {
					stopRun();
				} else {
					try {
						URLConnection con = null;
						String view = "", line = "";
						URL url = null;
						InputStream content = null;
						BufferedReader in = null;

						// check google if internet Wonderfull
						/*
						 * url = new URL("http://www.google.com"); con =
						 * url.openConnection(); con.setDoOutput(true);
						 * con.setConnectTimeout(10 * 1000); content =
						 * (InputStream) con.getContent(); in = new
						 * BufferedReader( new InputStreamReader(content));
						 * while ((line = in.readLine()) != null) { view +=
						 * line; }
						 */

						if (view.length() > 0) {
							// chack can syncronized? in local
							url = new URL(urlSEnd
									+ "/sync/check-server-can-syncronized");
							con = url.openConnection();
							con.setRequestProperty("computer_id",
									String.valueOf(computer_id));

							content = (InputStream) con.getContent();
							in = new BufferedReader(new InputStreamReader(
									content));
							line = "";
							view = "";
							while ((line = in.readLine()) != null) {
								view = line;
							}
							if (view.equalsIgnoreCase("t")) {
								// chack can syncronized? in server

								url = new URL(urlSErver
										+ "/sync/check-server-can-syncronized");
								con = url.openConnection();
								con.setRequestProperty("computer_id",
										String.valueOf(computer_id));

								content = (InputStream) con.getContent();
								in = new BufferedReader(new InputStreamReader(
										content));
								line = "";
								view = "";
								while ((line = in.readLine()) != null) {
									view = line;
								}

								if (view.equalsIgnoreCase("t")) {
									System.out.println("sync running....");
									url = new URL(urlSEnd + "/sync/upload-data");
									con = url.openConnection();
									con.setDoOutput(true);

									content = (InputStream) con.getContent();
									in = new BufferedReader(
											new InputStreamReader(content));
									view = "";
									line = "";
									while ((line = in.readLine()) != null) {
										view += line;
									}

									url = new URL(urlSEnd
											+ "/sync/download-data/" + userID
											+ "/" + companyID);
									con = url.openConnection();
									con.setDoOutput(true);

									content = (InputStream) con.getContent();
									in = new BufferedReader(
											new InputStreamReader(content));
									view = "";
									line = "";
									while ((line = in.readLine()) != null) {
										view += line;
									}

									variableSync = "";
									System.out.println("sync stop....");
								} else {
									variableSync = "pause";
								}
							} else {
								variableSync = "pause";
							}
						} else {
							variableSync = "notConnect";
						}
					} catch (Exception ex) {
						variableSync = "error = " + ex.getMessage();
						System.out
								.println(" error in timer " + ex.getMessage());
					}
				}

			}
		}, 0, 40 * 1000);
	}

	static void runSyncronize1(final String urlSEnd, final String urlSErver,
			final int computer_id, final String userID, final String companyID) {
		Timer timerIT1 = new Timer();
		timerIT1.schedule(new TimerTask() {
			public void run() {
				System.out.println(" run sysys ");
				String variableSync1 = "";

				try {
					URL url = null;
					URLConnection con = null;
					InputStream content = null;
					BufferedReader in = null;
					String view = "", line = "";

					url = new URL(urlSEnd + "/sync/get-my-identity");
					con = url.openConnection();
					con.setRequestProperty("computer_id", String.valueOf(computer_id));
					content = (InputStream) con.getContent();
					in = new BufferedReader(new InputStreamReader(content));
					while ((line = in.readLine()) != null) {
						view = line;
					}
					if (view.length() > 0) {
						url = new URL(urlSErver + "/sync/check-server-can-syncronized");
						con = url.openConnection();
						con.setRequestProperty("compIdentity", view);

						content = (InputStream) con.getContent();
						in = new BufferedReader(new InputStreamReader(content));
						while ((line = in.readLine()) != null) {
							view = line;
						}
						if (view.equalsIgnoreCase("t")) {
							System.out.println("sync running....");

							url = new URL(urlSEnd + "/sync/upload-data");
							con = url.openConnection();
							con.setRequestProperty("computer_id", String.valueOf(computer_id));
							con.setDoOutput(true);

							content = (InputStream) con.getContent();
							in = new BufferedReader(new InputStreamReader(content));
							while ((line = in.readLine()) != null) {
								view += line;
							}

							url = new URL(urlSEnd + "/sync/download-data/" + userID
									+ "/" + companyID);
							con = url.openConnection();
							con.setRequestProperty("computer_id", String.valueOf(computer_id));
							con.setDoOutput(true);

							content = (InputStream) con.getContent();
							in = new BufferedReader(new InputStreamReader(content));
							view = "";
							line = "";
							while ((line = in.readLine()) != null) {
								view += line;
							}

							variableSync1 = "";
							System.out.println("sync stop....");
						} else {
							variableSync1 = "notConnect";
						}
					}
				} catch (Exception ex) {
					variableSync1 = "error = " + ex.getMessage();
					System.out.println(" error in timer " + ex.getMessage());
				}
				System.out.println(" stop sysys ");
			}
		}, 0, 500);
	}

	static void runSyncronize2(final String urlSEnd, final String urlSErver,
			final int computer_id, final String userID, final String companyID) {
		System.out.println(" run sysys ");
		String variableSync1 = "";
		try {
			URL url = null;
			URLConnection con = null;
			InputStream content = null;
			BufferedReader in = null;
			String view = "", line = "";

			url = new URL(urlSEnd + "/sync/get-my-identity");
			con = url.openConnection();
			con.setRequestProperty("computer_id", String.valueOf(computer_id));
			content = (InputStream) con.getContent();
			in = new BufferedReader(new InputStreamReader(content));
			while ((line = in.readLine()) != null) {
				view = line;
			}
			if (view.length() > 0) {
				url = new URL(urlSErver + "/sync/check-server-can-syncronized");
				con = url.openConnection();
				con.setRequestProperty("compIdentity", view);

				content = (InputStream) con.getContent();
				in = new BufferedReader(new InputStreamReader(content));
				while ((line = in.readLine()) != null) {
					view = line;
				}
				if (view.equalsIgnoreCase("t")) {
					System.out.println("sync running....");

					url = new URL(urlSEnd + "/sync/upload-data");
					con = url.openConnection();
					con.setRequestProperty("computer_id", String.valueOf(computer_id));
					con.setDoOutput(true);

					content = (InputStream) con.getContent();
					in = new BufferedReader(new InputStreamReader(content));
					while ((line = in.readLine()) != null) {
						view += line;
					}

					url = new URL(urlSEnd + "/sync/download-data/" + userID
							+ "/" + companyID);
					con = url.openConnection();
					con.setRequestProperty("computer_id", String.valueOf(computer_id));
					con.setDoOutput(true);

					content = (InputStream) con.getContent();
					in = new BufferedReader(new InputStreamReader(content));
					view = "";
					line = "";
					while ((line = in.readLine()) != null) {
						view += line;
					}

					variableSync1 = "";
					System.out.println("sync stop....");
				} else {
					variableSync1 = "notConnect";
				}
			}
		} catch (Exception ex) {
			variableSync1 = "error = " + ex.getMessage();
			System.out.println(" error in timer " + ex.getMessage());
		}
		System.out.println(" stop sysys ");

	}

	public void stopRun() {
		timerIT.cancel();
	}

	public String getStatus() {
		if (variableSync == null)
			variableSync = "";
		return variableSync;
	}

	public static void main(String[] args) {
		runSyncronize1("http://localhost:8080/verse",
				"http://localhost:8080/verseserver", 1, "2", "2");
		// "http://verse.herokuapp.com", 1, "2", "2");
	}

}

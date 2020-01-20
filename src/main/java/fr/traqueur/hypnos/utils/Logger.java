package fr.traqueur.hypnos.utils;

public class Logger {

	public enum LogType {
		
		WARN("[WARN]"),
		INFO("[INFO]"),
		SUCCESS("[SUCCESS]"),
		ERROR("[ERROR]"),
		;
		
		private String prefix;
		
		private LogType(String prefix) {
			this.prefix = prefix;
		}
		
		public String getPrefix() {
			return this.prefix;
		}
	}
	
	private static String PREFIX = "[HypnosBot]";

	
	public static void log(LogType type, String log) {
		System.out.println(type.getPrefix() + PREFIX + " " + log);
	}
	
	public static void log(String log) {
		log(LogType.INFO, log);
	}
}

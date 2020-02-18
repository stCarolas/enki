module com.github.stcarolas.enki.gitlab {
	requires static lombok;
	requires com.github.stcarolas.enki.core;
	requires org.apache.logging.log4j;
	requires vavr;
	requires gitlab4j.api;

	exports com.github.stcarolas.enki.gitlab.provider;
}

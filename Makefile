SOURCE_DIR   := src/
OUTPUT_DIR   := bin/
FIND         := find
MKDIR        := mkdir -p
RM           := rm -rd
JAVAC        := javac
JFLAGS       := -sourcepath $(SOURCE_DIR) -d $(OUTPUT_DIR)
HAMCREST_JAR := lib/hamcrest-core-1.3.jar
JUNIT_JAR    := lib/junit-4.12.jar
CLASSPATH    := :./$(JUNIT_JAR):./$(HAMCREST_JAR):$(OUTPUT_DIR)
CONFIG   		 := config.xml shapes-base.csv

ifndef RPS_ARGS
	RPS_ARGS = $(CONFIG)
endif

# all_javas - Temp file for holding source file list
all_javas := $(OUTPUT_DIR)/all.javas

# make-directories - Ensure output directory exists.
make-directories := $(shell $(MKDIR) $(OUTPUT_DIR))

# add stuff to the classpath
export CLASSPATH := $(CLASSPATH)

# all - Perform all tasks for a complete build
.PHONY: all
all: compile

.PHONY: count
count:
	wc -l `find . -name *.java`

# all_javas - Gather source file list
.INTERMEDIATE: $(all_javas)
$(all_javas):
	$(FIND) $(SOURCE_DIR) -name '*.java' > $@


# compile - Compile the source
.PHONY: compile
compile: $(all_javas)
	$(JAVAC) $(JFLAGS) @$<

.PHONY: clean
clean:
	$(RM) $(OUTPUT_DIR)

.PHONY: classpath
classpath:
	export CLASSPATH='$(CLASSPATH)'

.PHONY: run
run: classpath
	java rps.Main $(RPS_ARGS)

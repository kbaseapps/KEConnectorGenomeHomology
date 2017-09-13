FROM kbase/kbase:sdkbase.latest
MAINTAINER KBase Developer
# -----------------------------------------
# In this section, you can install any system dependencies required
# to run your App.  For instance, you could place an apt-get update or
# install line here, a git checkout to download code, or run any other
# installation scripts.

# RUN apt-get update

RUN mkdir -p /kb/deps
WORKDIR /kb/deps
RUN curl -o blast.tar.gz 'ftp://ftp.ncbi.nlm.nih.gov/blast/executables/blast+/2.2.29/ncbi-blast-2.2.29+-x64-linux.tar.gz'
RUN tar -zxvf blast.tar.gz ncbi-blast-2.2.29+/bin/makeblastdb ncbi-blast-2.2.29+/bin/blastp

# -----------------------------------------

COPY ./ /kb/module
RUN mkdir -p /kb/module/bin
RUN mv /kb/deps/ncbi-blast-2.2.29+/bin/* /kb/module/bin/
RUN mkdir -p /kb/module/work
RUN chmod -R a+rw /kb/module

WORKDIR /kb/module

RUN make all

ENTRYPOINT [ "./scripts/entrypoint.sh" ]

CMD [ ]

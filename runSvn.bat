@echo off
rem delete old tag
echo delete %2-tag-%3-sit
svn delete http://10.1.7.3/repos/ilsmprep/%1/code/%2/devtags/%2-tag-%3-sit ^
	-m "Description: Reference:  by:yinkewei  Reviewed by:"
echo copy  %2-tag-%3-lt to %2-tag-%3-sit
rem copy tag
svn copy ^
	http://10.1.7.3/repos/ilsmprep/%1/code/%2/devtags/%2-tag-%3-lt ^
	http://10.1.7.3/repos/ilsmprep/%1/code/%2/devtags/%2-tag-%3-sit ^
	-m "Description: Reference:  by:yinkewei  Reviewed by:"
echo --------------------------------------------------------
<?php
	require_once '../../models/Audicao.php';

	// Ignorar Warnings do XSL 
    error_reporting(E_ERROR | E_PARSE);

    $idAudicao = $_REQUEST["idAudicao"];

    // Exportar audicao para XML
    $outputxmlfile = Audicao::exportarAudicaoParaXML($idAudicao);

    $audicao = simplexml_load_file($outputxmlfile);

	# LOAD XML FILE
	$xml = new DOMDocument();
	$xml->loadXML($audicao->asXML());

	# START XSLT
	$xslt = new XSLTProcessor();
	$XSL = new DOMDocument();
	$XSL->load( 'audicaotopdf.xsl', LIBXML_NOCDATA);
	$xslt->importStylesheet( $XSL );
	
	#PRINT
    $out = fopen("out.fo", 'w');
	fwrite($out, $xslt->transformToXML( $xml ));
    fclose($out);

    $date = new DateTime();
    $timestamp = $date->getTimestamp();
    $pdfname = "ficheiros/audicao_".$timestamp.".pdf";

    exec("fop out.fo ".$pdfname);
    exec("rm out.fo");

    echo "../../../../back-end/controllers/audicoes/".$pdfname;

?>

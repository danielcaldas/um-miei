<?php

	require_once "dropbox-sdk-php-1.1.5/lib/Dropbox/autoload.php";
	use \Dropbox as dbx;

	function uploadFileAndShare($FILENAME) {

		$dropbox_config = array(
		    'key'    => 'k9mkxandhdbbnhp',
		    'secret' => 'ie7ftqlwb0ns4fg'
		);

		$appInfo = dbx\AppInfo::loadFromJson($dropbox_config);
		$webAuth = new dbx\WebAuthNoRedirect($appInfo, "PHP-Example/1.0");

		$authorizeUrl = $webAuth->start();

		$accessToken = trim('j2vuCwO5a_8AAAAAAAAcCM01g5pI8oD5MUpzxhzRmIfJkM6JKQghXeZTyd2bCt2I');

		$dbxClient = new dbx\Client($accessToken, "PHP-Example/1.0");

		// Uploading do ficheiro
		$f = fopen($FILENAME, "rb");
		$result = $dbxClient->uploadFile("/".$FILENAME, dbx\WriteMode::add(), $f);
		fclose($f);

		// Obter informacao do ficheiro
		$file = $dbxClient->getMetadata("/".$FILENAME);

		// Obter o link direto para o ficheiro
		$dropboxPath = $file['path'];

		// Criar link publico
		$shareurl = $dbxClient->createShareableLink($dropboxPath);
		if ($shareurl === null) {
		    fwrite(STDERR, "File not found on Dropbox.\n");
		    die;
		}

		return $shareurl;
	}

?>
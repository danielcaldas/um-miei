<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
    xmlns:fo="http://www.w3.org/1999/XSL/Format"
    version="2.0">
    
    <xsl:template match="/">
        <fo:root>
            <fo:layout-master-set>
                <fo:simple-page-master master-name="audicao" page-height="297mm" page-width="210mm">
                    <fo:region-body region-name="atuacoes" margin="1cm"/>
                </fo:simple-page-master>
            </fo:layout-master-set>
            <xsl:apply-templates/>
        </fo:root>
    </xsl:template>
    
    <xsl:template match="audicao">
        <fo:page-sequence master-reference="audicao">
            <fo:flow flow-name="atuacoes" font-size="8pt">
                <fo:block font-size="16pt" text-align="center" space-after="0.3cm" space-before="0.3cm"><xsl:value-of select="titulo"/></fo:block>
                <fo:block font-size="11pt" text-align="center">Subtitulo: <xsl:value-of select="subtitulo"/></fo:block>
                <fo:block font-size="11pt" text-align="center">Tema: <xsl:value-of select="tema"/></fo:block>
                <fo:block font-size="11pt" text-align="center">Data: <xsl:value-of select="data"/></fo:block>
                <fo:block font-size="11pt" text-align="center">Hora de Inicio: <xsl:value-of select="horainicio"/></fo:block>
                <fo:block font-size="11pt" text-align="center">Hora de Fim: <xsl:value-of select="horafim"/></fo:block>
                <fo:block font-size="11pt" text-align="center">Duração: <xsl:value-of select="duracao"/></fo:block>
                <fo:block font-size="11pt" text-align="center">Local: <xsl:value-of select="local"/></fo:block>
                <fo:block font-size="11pt" text-align="center" space-after="0.3cm" space-before="0.3cm">Programa</fo:block>
                <xsl:apply-templates select="atuacoes"/>
            </fo:flow>
        </fo:page-sequence>
    </xsl:template>
    
    <xsl:template match="alunos">
        <fo:block border-bottom-width="0.5pt" border-bottom-style="solid" border-bottom-color="black"></fo:block>
        <fo:block space-after="0.2cm" space-before="0.2cm">
            <fo:block font-weight="bold">Alunos</fo:block><xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="aluno">
        <fo:block>
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="obras">
        <fo:block space-after="0.2cm" space-before="0.2cm">
            <fo:block font-weight="bold">Obras</fo:block><xsl:apply-templates/>
        </fo:block>      
    </xsl:template>
    
    <xsl:template match="obra">
        <fo:block>
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="professores">
        <fo:block space-after="0.2cm" space-before="0.2cm">
            <fo:block font-weight="bold">Professores</fo:block><xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="professor">
        <fo:block>
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="acompanhantes">
        <fo:block space-after="0.2cm" space-before="0.2cm">
            <fo:block font-weight="bold">Acompanhantes</fo:block><xsl:apply-templates/>
        </fo:block>
    </xsl:template>
    
    <xsl:template match="acompanhante">
        <fo:block>
            <xsl:apply-templates/>
        </fo:block>
    </xsl:template>
</xsl:stylesheet>
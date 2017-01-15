<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fo="http://www.w3.org/1999/XSL/Format"
  version="1.1" exclude-result-prefixes="fo">
  <xsl:output method="xml" version="1.0" omit-xml-declaration="no" indent="yes" />
  <xsl:param name="versionParam" select="'1.0'" />
  <xsl:template match="/data">
    <fo:root font-family="Arial" xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simpleA4" page-height="29.7cm" page-width="21cm"
          margin-top="2.6cm" margin-bottom="2.6cm" margin-left="2.6cm" margin-right="2.6cm">
          <fo:region-body />
        </fo:simple-page-master>
        <fo:simple-page-master master-name="simpleA42" page-height="29.7cm" page-width="21cm"
          margin-top="3.7cm" margin-bottom="2.6cm" margin-left="2.6cm" margin-right="2.6cm">
          <fo:region-body />
        </fo:simple-page-master>
      </fo:layout-master-set>

      <xsl:call-template name="page1" />
      <xsl:call-template name="page1" />
      <xsl:call-template name="page2" />

    </fo:root>
  </xsl:template>

  <xsl:template name="page1">
    <fo:page-sequence master-reference="simpleA4">
      <fo:flow flow-name="xsl-region-body">
        <fo:block font-size="18pt" font-weight="bold">
          <xsl:value-of select="header" />
        </fo:block>
        <fo:block font-size="14pt" space-before="3pt">Rezsi költségek</fo:block>

        <fo:block-container space-before="16pt" font-size="11pt" text-align-last="justify"
          end-indent="88pt">
          <xsl:apply-templates select="bill" />
          <fo:block space-before="15pt" font-weight="bold">
            Összesen:
            <fo:leader leader-pattern="space" />
            <xsl:value-of select="sum" />
            Ft
          </fo:block>
        </fo:block-container>

        <fo:block-container font-size="11pt">
          <fo:block space-before="153pt" font-weight="bold" text-decoration="underline">Óraállások</fo:block>
          <fo:block space-before="4pt">Villanyóra:</fo:block>
          <fo:block space-before="2pt">Vízóra hideg:</fo:block>
          <fo:block space-before="2pt">Vízóra meleg:</fo:block>
        </fo:block-container>

        <fo:block font-size="11pt" space-before="50pt" font-weight="bold">
          Szeged,
          <xsl:value-of select="date" />
        </fo:block>

        <fo:block-container font-size="11pt" space-before="48pt" text-align-last="justify">
          <fo:block>
            ..........................................
            <fo:leader leader-pattern="space" />
            ..........................................
          </fo:block>
          <fo:block start-indent="33pt" end-indent="22pt">
            <xsl:value-of select="tenant" />
            <fo:leader leader-pattern="space" />
            <xsl:value-of select="landLord" />
          </fo:block>
          <fo:block start-indent="33pt" end-indent="16pt">
            Bérlő/átadó
            <fo:leader leader-pattern="space" />
            Bérbeadó/átvevő
          </fo:block>
        </fo:block-container>
      </fo:flow>
    </fo:page-sequence>
  </xsl:template>

  <xsl:template name="page2">
    <fo:page-sequence master-reference="simpleA42">
      <fo:flow flow-name="xsl-region-body">

        <fo:block font-size="14pt" text-align-last="center" text-decoration="underline">Átvételi elismervény</fo:block>
        <fo:block space-before="98pt" font-size="11pt">Alulírott <xsl:value-of select="landLord" /> a lakbér összegét <xsl:value-of select="rentValue" /> Ft-t, azaz
          <xsl:value-of select="rentValueText" /> forintot átvettem.
        </fo:block>
        <fo:block space-before="184pt" font-size="11pt">
          Szeged,
          <xsl:value-of select="date" />
        </fo:block>
        <fo:block space-before="48pt" font-size="11pt">..........................................</fo:block>
        <fo:block start-indent="25pt" font-size="11pt"><xsl:value-of select="landLord" /></fo:block>
      </fo:flow>
    </fo:page-sequence>
  </xsl:template>
  <xsl:template match="bill">
    <fo:block space-before="2pt">
      <xsl:value-of select="name" />
      <fo:leader leader-pattern="space" />
      <xsl:value-of select="price" />
      Ft
    </fo:block>
  </xsl:template>
</xsl:stylesheet>
<?xml version="1.0" encoding="UTF-8"?>

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:e="http://it.univaq.mwt.xml/epubmanager"
                xmlns:ff="http://www.w3.org/TR/xpath-functions"
                xmlns:xs="http://www.w3.org/2001/XMLSchema"
                xmlns:err="http://www.w3.org/2005/xqt-errors"
                xmlns:fn="http://www.w3.org/2005/xpath-functions">
    <xsl:output method="html" encoding="utf-8" indent="yes"/>

    <xsl:template match="/">
        <html>
        
        <h2><i>Epub Biblio</i></h2>
        
        <xsl:call-template name="vistaEpub"/>
        
        <table id="tavola" border="1">
            
            <tr bgcolor="#9acd32">
                <td style="text-align:left">
                    <p>
                        <b>Numero di ePub</b>
                    </p>
                </td>
                <td style="text-align:left">
                    <p>
                        <small>
                            <xsl:call-template name="numeroEpub"/>
                        </small>
                    </p>
                </td>
            </tr>
            
            <tr bgcolor="#9acd32">
                <td style="text-align:left">
                    <p>
                        <b>Autore più presente</b>
                    </p>
                </td>
                <td style="text-align:left">
                    <p>
                        <small>
                            <xsl:call-template name="mostAuthor"/>
                        </small>
                    </p>
                </td>
            </tr>
        </table>
        </html>
    </xsl:template>
    <xsl:template name="numeroEpub">
        <xsl:value-of select="count(//e:ePub)"/>           
    </xsl:template>
    <xsl:template name="mostAuthor">
        <xsl:for-each select="//e:lista-statistiche/e:StatisticheAutori/e:author">
            <xsl:sort select="e:frequenza/text()" data-type="numbrer" order="descending" />
            <xsl:if test="position() = 1">
                <xsl:value-of select="e:nome"/>
            </xsl:if>
        </xsl:for-each>
    </xsl:template> 
       
    <xsl:template name="vistaEpub">
        <table id="tavola2" border="1">
            <tr bgcolor="#9acd32">
                <td style="text-align:left">
                    <p>
                        <b> Titolo</b>
                    </p>
                </td>
                <td style="text-align:left">
                    <p>
                        <b> Autore</b>
                    </p>
                </td>
                <td style="text-align:left">
                    <p>
                        <b> Lingua</b>
                    </p>
                </td>

                
                <td style="text-align:left">
                    <p>
                        <b> #Immagini</b>
                    </p>
                </td>
                <td style="text-align:left">
                    <p>
                        <b> #Parole</b>
                    </p>
                </td>
                <td style="text-align:left">
                    <p>
                        <b> #Capitoli</b>
                    </p>
                </td>
                <td style="text-align:left">
                    <p>
                        <b> Percorso file</b>
                    </p>
                </td>
            </tr>
               
            <xsl:for-each select="//e:ePub">
                <tr bgcolor="#9acd32">
                    <td style="text-align:left">
                        <p>
                            <small>
                                <xsl:value-of select="e:titolo/text()" />
                            </small>
                        </p>
                    </td>
                    <td style="text-align:left">
                        <p>
                            <small>
                                <xsl:value-of select="e:autore/text()" />
                            </small>
                        </p>
                    </td>
                    <td style="text-align:left">
                        <p>
                            <small>
                                <xsl:value-of select="e:lingua/text()" />
                            </small>
                        </p>
                    </td>
                    
                    
                    <td style="text-align:left">
                        <p>
                            <small>
                                <xsl:value-of select="e:immagini/text()" />
                            </small>
                        </p>
                    </td>
                    <td style="text-align:left">
                        <p>
                            <small>
                                <xsl:value-of select="e:parole/text()" />
                            </small>
                        </p>
                    </td>
                    <td style="text-align:left">
                        <p>
                            <small>
                                <xsl:value-of select="e:capitoli/text()" />
                            </small>
                        </p>
                    </td>
                    <td style="text-align:left">
                        <p>
                            <small>
                                <xsl:value-of select="e:percorsofile/text()" />
                            </small>
                        </p>
                    </td>
                </tr>
            </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>
       

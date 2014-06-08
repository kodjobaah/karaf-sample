<xsl:stylesheet version="1.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:cus="http://customer.service.esb.tecplata.com/">
	<xsl:output method="xml" indent="yes" />

	<xsl:template match="/">
		<cus:getCustomerByCuitResponse>
			<!--Optional: -->
			<customer>
				<address_1>12</address_1>
				<address_2>12</address_2>
				<address_3>12</address_3>
				<city>12</city>
				<contactName>12</contactName>
				<country>12</country>
				<creditLimit>12</creditLimit>
				<cuit>12</cuit>
				<email>12</email>
				<fax>12</fax>
				<name>12</name>
				<phone>12</phone>
				<stateName>12</stateName>
				<taxGroup>
					<description>12</description>
					<id>12</id>
				</taxGroup>
			</customer>
		</cus:getCustomerByCuitResponse>

	</xsl:template>
</xsl:stylesheet>
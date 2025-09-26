package com.example.wedstoryes.presentation

import androidx.compose.material.icons.filled.Share


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PdfQuotationScreen(
    onDownloadPdf: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5))
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            // Preview Header
            PreviewHeader()

            Spacer(modifier = Modifier.height(16.dp))

            // PDF Preview Content
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .shadow(8.dp, RoundedCornerShape(8.dp)),
                shape = RoundedCornerShape(8.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .padding(20.dp)
                ) {
                    // Header
                    QuotationHeader()

                    Spacer(modifier = Modifier.height(16.dp))

                    // Client and Company Info
                    ClientCompanyInfo()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Thank you message
                    Text(
                        text = "Dear Komali,",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "Thank you for choosing The wedstoryes for your big day. Please find the quotation below.",
                        fontSize = 14.sp,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Events Table
                    EventsTable()

                    Spacer(modifier = Modifier.height(20.dp))

                    // Photobook Details
                    PhotobookSection()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Terms and Conditions
                    TermsAndConditions()

                    Spacer(modifier = Modifier.height(24.dp))

                    // Amount Summary
                    AmountSummary()
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Download PDF Button
            Button(
                onClick = onDownloadPdf,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Download",
                    modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Text(
                    text = "Download PDF",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
fun PreviewHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Quotation Preview",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = "Komali - Wedding Package",
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun QuotationHeader() {
    Text(
        text = "Quotation",
        fontSize = 24.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = "Date: 23-Jul-25",
        fontSize = 14.sp,
        modifier = Modifier.fillMaxWidth(),
        textAlign = TextAlign.Center
    )
}

@Composable
fun ClientCompanyInfo() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        // To: Section
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "To:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Komali",
                fontSize = 14.sp
            )
            Text(
                text = "rajeshputti21@gmail.com",
                fontSize = 14.sp
            )
            Text(
                text = "+91 80088 92188",
                fontSize = 14.sp
            )
        }

        // From: Section
        Column(
            modifier = Modifier.weight(1f),
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "From:",
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "The wedstoryes",
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Text(
                text = "thewedstoryes2019@gmail.com",
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
            Text(
                text = "9030709090",
                fontSize = 14.sp,
                textAlign = TextAlign.End
            )
        }
    }
}

@Composable
fun EventsTable() {
    // Table Header
    Row(
        modifier = Modifier
            .fillMaxWidth().background(color = Color.Red)
            .border(1.dp, Color.Black)
            .padding(8.dp)
    ) {
        TableHeaderCell("Event", 0.15f)
        TableHeaderCell("Photographers", 0.2f)
        TableHeaderCell("Videographers", 0.2f)
        TableHeaderCell("Date", 0.15f)
        TableHeaderCell("Time", 0.1f)
        TableHeaderCell("Addons", 0.1f)
        TableHeaderCell("Price", 0.1f)
    }

    // Engagement Row
    EventRow(
        event = "Engagement",
        photographers = "1 Candid\n1 Traditional",
        videographers = "1 Candid\n1 Traditional",
        date = "3-Aug-25",
        time = "Morning",
        addons = "Drone",
        price = "0"
    )

    // Haldi Row
    EventRow(
        event = "Haldi",
        photographers = "1 Candid\n1 Traditional",
        videographers = "1 Candid\n1 Traditional",
        date = "22-Sep-25",
        time = "Morning",
        addons = "Drone",
        price = "0"
    )

    // Nalugu Row
    EventRow(
        event = "Nalugu",
        photographers = "1 Traditional",
        videographers = "1 Traditional",
        date = "23-Sep-25",
        time = "Evening",
        addons = "",
        price = "0"
    )

    // Wedding Row
    EventRow(
        event = "Wedding",
        photographers = "1 Candid\n1 Traditional",
        videographers = "1 Candid\n2 Traditional",
        date = "24-Sep-25",
        time = "Evening",
        addons = "Drone\nLED Screen x 1\nLive Streaming",
        price = "0"
    )
}

@Composable
fun EventRow(
    event: String,
    photographers: String,
    videographers: String,
    date: String,
    time: String,
    addons: String,
    price: String
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color.LightGray)
            .padding(8.dp)
    ) {
        TableCell(event, 0.15f)
        TableCell(photographers, 0.2f)
        TableCell(videographers, 0.2f)
        TableCell(date, 0.15f)
        TableCell(time, 0.1f)
        TableCell(addons, 0.1f)
        TableCell(price, 0.1f)
    }
}

@Composable
fun RowScope.TableHeaderCell(text: String, weight: Float) {
    Text(
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.weight(weight),
        textAlign = TextAlign.Center,
        color = Color.White
    )
}

@Composable
fun RowScope.TableCell(text: String, weight: Float) {
    Text(
        text = text,
        fontSize = 11.sp,
        modifier = Modifier.weight(weight),
        textAlign = TextAlign.Center
    )
}

@Composable
fun PhotobookSection() {
    // Photobook Header
    Row(
        modifier = Modifier
            .fillMaxWidth().background(Color.Red)
            .padding(8.dp)
    ) {
        TableHeaderCell("Photobook", 0.25f)
        TableHeaderCell("Sheets", 0.25f)
        TableHeaderCell("Description", 0.25f)
        TableHeaderCell("Price", 0.25f)
    }

    // Photobook Row
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(0.5.dp, Color.LightGray)
            .padding(8.dp)
    ) {
        TableCell("Premium\nAlbums", 0.25f)
        TableCell("3", 0.25f)
        TableCell("80 Silky matte sheets", 0.25f)
        TableCell("235000", 0.25f)
    }
}

@Composable
fun TermsAndConditions() {
    Text(
        text = "Terms and Conditions:",
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold
    )

    Spacer(modifier = Modifier.height(8.dp))

    val terms = listOf(
        "1. 50,000/- advance to confirm the booking, Engagement day 30,000/- and balance on event day.",
        "2. Photos and videos will be delivered in 50-65 days, client selected pictures.",
        "3. 80 sheets premium Albums set of 3 premium Albums Additional sheet cost 650 per sheet.",
        "4. Full length engagement wedding Teaser.",
        "5. Four traditional full length videos.",
        "6. On wedding day client have to pay 1,15,000/- Once Album design completed, If any change's Is their we will be reedit it.printing sending day have to pay 40,000/-Albums videos will be delivered 15-20 days.",
        "7. Client have to purchase 2 hard disks",
        "8. Advance amount not refundable. Not applicable for another event.",
        "9. Advance 50,000/- Engagement day 30,000/- wedding day 1,15,000/- album printing day 40,000/-.",
        "10. Stay and food Allowances client only Accommodate."
    )

    terms.forEach { term ->
        Text(
            text = term,
            fontSize = 12.sp,
            modifier = Modifier.padding(bottom = 4.dp)
        )
    }
}

@Composable
fun AmountSummary() {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.End
    ) {
        Text(
            text = "Amount",
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Subtotal", fontSize = 14.sp)
            Text("235000.00", fontSize = 14.sp)
        }

        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Discount", fontSize = 14.sp)
            Text("0", fontSize = 14.sp)
        }

        Divider(
            modifier = Modifier.width(200.dp),
            color = Color.Black,
            thickness = 1.dp
        )

        Row(
            modifier = Modifier.width(200.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Total",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "235000.00",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
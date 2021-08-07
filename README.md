# Stock-market-simulation
Stock market sumulation in Java, without GUI

## Input file for Creating companies and transactions
Add scrip: WIPRO, sector: IT, O:350, H:352.8, L:357, C:352
Add scrip: HCLTECH, sector: IT, O:866, H:872, L:847, C:855
Add scrip: CUB, sector: Bank, O:160, H:175, L:155, C:165
Add scrip: TATAMOTORS, sector: Auto, O:121, H:130, L:117, C:128
Add scrip: ASHOKLEY, sector: Auto, O:75, H:75.5, L:69.05, C:70.2
Add scrip: CIPLA, sector: Pharma, O:761, H:789.5, L:760, C:763.2
Add scrip: SUNPHARMA, sector: Pharma, O:510, H:529.95, L:510, C:524.5
Add scrip: BIOCON, sector: Pharma, O:406, H:420, L:400.05, C:416
Add scrip: LUPIN, sector: Pharma, O:908, H:925, L:902.05, C:917.5
Add user: Jaydeep, funds:155000 holding: {WIPRO:330, CUB:500, SUNPHARMA:90}
Add user: Mimi, funds:300000 holding: {WIRRO:250, HCLTECH:50, TATAMOTORS:50, ASHOKLEY:700}
Add user: Kapil, funds:250000 holding: {SUNPHARMA:100, CIPLA:50, BIOCON:90, LUPIN:400}
Add user: Nusrat, funds:690000 holding: None
Place order, user: Jaydeep, type: buy, scrip: WIPRO, qty:100, rate: 315
Place order, user: Jaydeep, type: buy, scrip: WIPRO, qty:100, rate: 335
Place order, user: Kapil, type: buy, scrip: TATAMOTORS, qty:100, rate: 120
Place order, user: Nusrat, type: buy, scrip: LUPIN, qty:900, rate: 900
Place order, user: Mimi, type: sell, scrip: WIPRO, qty:250, rate: 335
Place order, user: Jaydeep, type: sell, scrip: SUNPHARMA, qty:250, rate: 505
Place order, user: Mimi, type: sell, scrip: TATAMOTORS, qty:200, rate: 116
Place order, user: Jaydeep, type: sell, scrip: CUB, qty:250, rate: 200
Show Orderbook
Execute
Show sector: IT
Delete scrip: M&M
Delete User: Nusrat
Show Users
Exit

## Output file to show all companies and successful transactions

Scrip Added: WIPRO, Open: 350.0, High: 352.8, Low: 357.0, Close: 352.0, Markets: NSE & BSE both, Total Scrips: 1
Scrip Added: HCLTECH, Open: 866.0, High: 872.0, Low: 847.0, Close: 855.0, Markets: BSE only, Total Scrips: 2
Scrip Added: CUB, Open: 160.0, High: 175.0, Low: 155.0, Close: 165.0, Markets: BSE only, Total Scrips: 3
Scrip Added: TATAMOTORS, Open: 121.0, High: 130.0, Low: 117.0, Close: 128.0, Markets: NSE & BSE both, Total Scrips: 4
Scrip Added: ASHOKLEY, Open: 75.0, High: 75.5, Low: 69.05, Close: 70.2, Markets: NSE only, Total Scrips: 5
Scrip Added: CIPLA, Open: 761.0, High: 789.5, Low: 760.0, Close: 763.2, Markets: BSE only, Total Scrips: 6
Scrip Added: SUNPHARMA, Open: 510.0, High: 529.95, Low: 510.0, Close: 524.5, Markets: NSE only, Total Scrips: 7
Scrip Added: BIOCON, Open: 406.0, High: 420.0, Low: 400.05, Close: 416.0, Markets: NSE only, Total Scrips: 8
Scrip Added: LUPIN, Open: 908.0, High: 925.0, Low: 902.05, Close: 917.5, Markets: BSE only, Total Scrips: 9
User added: Jaydeep, Funds: 155000.0, Number of holdings: 3, Customer ID: 210254, Holdings: {CUB=500, WIPRO=330, SUNPHARMA=90}
User added: Mimi, Funds: 300000.0, Number of holdings: 3, Customer ID: 521237, Holdings: {ASHOKLEY=700, HCLTECH=50, TATAMOTORS=50}
User added: Kapil, Funds: 250000.0, Number of holdings: 4, Customer ID: 788744, Holdings: {BIOCON=90, SUNPHARMA=100, LUPIN=400, CIPLA=50}
User added: Nusrat, Funds: 690000.0, Number of holdings: 0, Customer ID: 856400, Holdings: {}
Order rejected for User: Jaydeep Reason: Lower circuit violation
order placed for user: Jaydeep, Type: buy, Scrip: WIPRO, Qty: 100, Rate: 335.0
order placed for user: Kapil, Type: buy, Scrip: TATAMOTORS, Qty: 100, Rate: 120.0
Order rejected for User: Nusrat Reason: Insufficient Funds
Order rejected as there are no stocks found in User holdings, User: Mimi
Order rejected for User: Jaydeep Reason: Short Selling Out of scope
Order rejected for User: Mimi Reason: Short Selling Out of scope
Order rejected for User: Jaydeep Reason: Upper circuit violation

Order Book:
Buy order WIPRO:100 at 335.0
Buy order TATAMOTORS:100 at 120.0

Executed orders:

Scrips listed in category: IT
WIPRO, OHLC = <350.0, 352.8, 357.0, 352.0>, Markets: NSE & BSE both
HCLTECH, OHLC = <866.0, 872.0, 847.0, 855.0>, Markets: NSE & BSE both

Scrip not found: M&M
Deleted user: Nusrat

Users:
Name: Jaydeep, Funds: 155000.0, Holdings: {CUB=500, WIPRO=330, SUNPHARMA=90}
Name: Mimi, Funds: 300000.0, Holdings: {ASHOKLEY=700, HCLTECH=50, TATAMOTORS=50}
Name: Kapil, Funds: 250000.0, Holdings: {BIOCON=90, SUNPHARMA=100, LUPIN=400, CIPLA=50}

INFY 15 Day Data:
Open average of 15 days: 348.54
Close average of 15 days: 349.2
Overall average: 348.87
Max return potential over 15 days: 98.35 per share
Max return potential percentage: 31.2%
Max Drawdown: 37.85

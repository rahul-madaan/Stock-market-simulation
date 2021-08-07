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

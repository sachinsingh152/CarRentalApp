const puppeteer = require('puppeteer');
const fs = require('fs');

(async () => {
    // Ensure screenshots directory exists
    if (!fs.existsSync('screenshots')) {
        fs.mkdirSync('screenshots');
    }

    const browser = await puppeteer.launch({
        headless: "new",
        args: ['--no-sandbox', '--disable-setuid-sandbox']
    });
    
    const page = await browser.newPage();
    await page.setViewport({ width: 1200, height: 800 });

    // 1. Web Login
    await page.goto('http://localhost:8080/index.html');
    // Wait for form
    await page.waitForSelector('#login-form');
    await page.screenshot({ path: 'screenshots/web-login.png' });
    
    // Login to access other pages
    await page.type('#username', 'admin');
    await page.type('#password', 'admin123');
    await page.click('#login-btn');
    
    // Wait for redirect to dashboard
    await page.waitForNavigation();
    
    // 2. Web Dashboard
    await new Promise(r => setTimeout(r, 1000)); // wait for API calls to populate stats
    await page.screenshot({ path: 'screenshots/web-dashboard.png' });
    
    // 3. Web Cars
    await page.click('[data-target="cars-view"]');
    await new Promise(r => setTimeout(r, 500));
    await page.screenshot({ path: 'screenshots/web-cars.png' });
    
    // 4. Web Rent
    await page.click('[data-target="rent-view"]');
    await new Promise(r => setTimeout(r, 500));
    await page.screenshot({ path: 'screenshots/web-rent.png' });

    // 5. Web Customers/Returns
    await page.click('[data-target="customers-view"]');
    await new Promise(r => setTimeout(r, 500));
    await page.screenshot({ path: 'screenshots/web-customers.png' });

    // Rename existing javaFx screenshot to desktop-app.png just to represent it
    if (fs.existsSync('screenshots/dashboard.png')) {
        fs.copyFileSync('screenshots/dashboard.png', 'screenshots/desktop-app.png');
    }

    await browser.close();
    console.log('Screenshots captured successfully!');
})();

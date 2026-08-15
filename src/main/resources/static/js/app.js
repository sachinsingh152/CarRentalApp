document.addEventListener('DOMContentLoaded', () => {
    // 1. Auth Check & Routing
    const isLoginPage = window.location.pathname.endsWith('index.html') || window.location.pathname === '/';
    const role = localStorage.getItem('role');
    
    if (isLoginPage) {
        if (role) window.location.href = 'dashboard.html';
        initLogin();
    } else {
        if (!role) window.location.href = 'index.html';
        initDashboard();
    }
});

// LOGIN LOGIC
function initLogin() {
    const form = document.getElementById('login-form');
    if (!form) return;
    
    form.addEventListener('submit', async (e) => {
        e.preventDefault();
        const username = document.getElementById('username').value;
        const password = document.getElementById('password').value;
        const btn = document.getElementById('login-btn');
        const errDiv = document.getElementById('login-error');
        
        btn.innerHTML = 'Signing in...';
        btn.disabled = true;
        
        try {
            const res = await fetch('/api/auth/login', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ username, password })
            });
            const data = await res.json();
            
            if (res.ok) {
                localStorage.setItem('role', data.role);
                window.location.href = 'dashboard.html';
            } else {
                errDiv.textContent = data.message || 'Login failed';
                errDiv.style.display = 'block';
            }
        } catch (err) {
            errDiv.textContent = 'Network error. Please try again.';
            errDiv.style.display = 'block';
        } finally {
            btn.innerHTML = '<span>Sign In</span>';
            btn.disabled = false;
        }
    });
}

// DASHBOARD LOGIC
function initDashboard() {
    // Navigation
    document.querySelectorAll('.nav-item').forEach(item => {
        item.addEventListener('click', (e) => {
            if (item.id === 'logout-btn') {
                localStorage.removeItem('role');
                window.location.href = 'index.html';
                return;
            }
            e.preventDefault();
            
            document.querySelectorAll('.nav-item').forEach(nav => nav.classList.remove('active'));
            document.querySelectorAll('.view-section').forEach(sec => sec.classList.remove('active'));
            
            item.classList.add('active');
            const target = item.getAttribute('data-target');
            document.getElementById(target).classList.add('active');
            
            refreshView(target);
        });
    });

    // Modals
    document.querySelector('.close-modal').addEventListener('click', () => {
        document.getElementById('car-modal').classList.remove('show');
    });

    // Forms
    document.getElementById('car-form').addEventListener('submit', handleCarSubmit);
    document.getElementById('rent-form').addEventListener('submit', handleRentSubmit);

    // Initial Load
    refreshView('dashboard-view');
}

function refreshView(view) {
    if (view === 'dashboard-view') loadDashboardStats();
    if (view === 'cars-view') loadCars();
    if (view === 'customers-view') loadCustomers();
    if (view === 'rent-view') populateRentCars();
}

async function loadDashboardStats() {
    try {
        const res = await fetch('/api/dashboard');
        const stats = await res.json();
        document.getElementById('stat-total-cars').textContent = stats.totalCars;
        document.getElementById('stat-available-cars').textContent = stats.availableCars;
        document.getElementById('stat-total-customers').textContent = stats.totalCustomers;
        document.getElementById('stat-total-income').textContent = '$' + parseFloat(stats.totalIncome || 0).toFixed(2);
    } catch (e) {
        console.error(e);
    }
}

async function loadCars() {
    try {
        const res = await fetch('/api/cars');
        const cars = await res.json();
        const tbody = document.querySelector('#cars-table tbody');
        tbody.innerHTML = '';
        
        cars.forEach(car => {
            const tr = document.createElement('tr');
            const badgeClass = car.status === '--Available--' ? 'status-available' : 'status-unavailable';
            tr.innerHTML = `
                <td>${car.carId}</td>
                <td>${car.brand}</td>
                <td>${car.model}</td>
                <td>$${car.price}</td>
                <td><span class="status-badge ${badgeClass}">${car.status}</span></td>
                <td>
                    <button class="btn btn-primary" style="padding: 5px 10px; font-size: 0.8rem" onclick="editCar('${car.carId}', '${car.brand}', '${car.model}', '${car.price}', '${car.status}')">Edit</button>
                    <button class="btn btn-danger" style="padding: 5px 10px; font-size: 0.8rem" onclick="deleteCar('${car.carId}')">Delete</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) {
        console.error(e);
    }
}

async function loadCustomers() {
    try {
        const res = await fetch('/api/customers');
        const customers = await res.json();
        const tbody = document.querySelector('#customers-table tbody');
        tbody.innerHTML = '';
        
        customers.forEach(c => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${c.customerId}</td>
                <td>${c.carId}</td>
                <td>${c.firstName} ${c.lastName}</td>
                <td>${c.dateRented || ''}</td>
                <td>${c.dateReturn || ''}</td>
                <td>$${c.total}</td>
                <td>
                    <button class="btn btn-primary" style="padding: 5px 10px; font-size: 0.8rem" onclick="returnCar('${c.carId}')">Return Car</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) {
        console.error(e);
    }
}

async function populateRentCars() {
    try {
        const res = await fetch('/api/cars');
        const cars = await res.json();
        const select = document.getElementById('rent-car-id');
        select.innerHTML = '<option value="">Select a car...</option>';
        
        cars.filter(c => c.status === '--Available--').forEach(car => {
            const opt = document.createElement('option');
            opt.value = car.carId;
            opt.textContent = `${car.carId} - ${car.brand} ${car.model} ($${car.price}/day)`;
            select.appendChild(opt);
        });
    } catch (e) {
        console.error(e);
    }
}

function showAddCarModal() {
    document.getElementById('car-form').reset();
    document.getElementById('car-id').readOnly = false;
    document.getElementById('car-edit-mode').value = 'false';
    document.getElementById('car-modal-title').textContent = 'Add New Car';
    document.getElementById('car-message').style.display = 'none';
    document.getElementById('car-modal').classList.add('show');
}

function editCar(id, brand, model, price, status) {
    document.getElementById('car-id').value = id;
    document.getElementById('car-id').readOnly = true;
    document.getElementById('car-brand').value = brand;
    document.getElementById('car-model').value = model;
    document.getElementById('car-price').value = price;
    document.getElementById('car-status').value = status;
    document.getElementById('car-edit-mode').value = 'true';
    document.getElementById('car-modal-title').textContent = 'Edit Car';
    document.getElementById('car-message').style.display = 'none';
    document.getElementById('car-modal').classList.add('show');
}

async function handleCarSubmit(e) {
    e.preventDefault();
    const isEdit = document.getElementById('car-edit-mode').value === 'true';
    const payload = {
        carId: document.getElementById('car-id').value,
        brand: document.getElementById('car-brand').value,
        model: document.getElementById('car-model').value,
        price: document.getElementById('car-price').value,
        status: document.getElementById('car-status').value
    };
    
    const method = isEdit ? 'PUT' : 'POST';
    const url = isEdit ? `/api/cars/${payload.carId}` : '/api/cars';
    const msgDiv = document.getElementById('car-message');
    
    try {
        const res = await fetch(url, {
            method: method,
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        
        msgDiv.textContent = data.message || data.error;
        msgDiv.className = res.ok ? 'message success' : 'message error';
        
        if (res.ok) {
            loadCars();
            setTimeout(() => document.getElementById('car-modal').classList.remove('show'), 1000);
        }
    } catch (err) {
        msgDiv.textContent = 'Error communicating with server';
        msgDiv.className = 'message error';
    }
}

async function deleteCar(id) {
    if (!confirm('Are you sure you want to delete car ' + id + '?')) return;
    try {
        await fetch(`/api/cars/${id}`, { method: 'DELETE' });
        loadCars();
    } catch (e) {
        console.error(e);
    }
}

async function handleRentSubmit(e) {
    e.preventDefault();
    const payload = {
        carId: document.getElementById('rent-car-id').value,
        firstName: document.getElementById('rent-first-name').value,
        lastName: document.getElementById('rent-last-name').value,
        gender: document.getElementById('rent-gender').value,
        rentDate: document.getElementById('rent-date').value,
        returnDate: document.getElementById('rent-return-date').value
    };
    
    const msgDiv = document.getElementById('rent-message');
    
    try {
        const res = await fetch('/api/rentals', {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(payload)
        });
        const data = await res.json();
        
        msgDiv.textContent = data.message ? `${data.message} (Total: $${data.price})` : data.error;
        msgDiv.className = res.ok ? 'message success' : 'message error';
        
        if (res.ok) {
            document.getElementById('rent-form').reset();
            populateRentCars();
        }
    } catch (err) {
        msgDiv.textContent = 'Error communicating with server';
        msgDiv.className = 'message error';
    }
}

async function returnCar(carId) {
    if (!confirm(`Process return for car ${carId}?`)) return;
    try {
        const res = await fetch(`/api/rentals/${carId}/return`, { method: 'POST' });
        if (res.ok) {
            alert('Car returned successfully!');
            loadCustomers();
        } else {
            const data = await res.json();
            alert(data.error || 'Failed to return car');
        }
    } catch (e) {
        console.error(e);
    }
}

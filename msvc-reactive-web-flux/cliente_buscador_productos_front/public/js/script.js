let productos = [];

async function cargarProductos() {
    const loading = document.getElementById('loading');
    const errorMsg = document.getElementById('errorMsg');
    try {
        loading.style.display = '';
        errorMsg.style.display = 'none';
        const res = await fetch('http://localhost:8082/api/v1/products/');
        if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
        productos = await res.json();
        renderizarProductos(productos);
    } catch (err) {
        errorMsg.textContent = 'Error al cargar productos: ' + err.message;
        errorMsg.style.display = '';
    } finally {
        loading.style.display = 'none';
    }
}

function renderizarProductos(lista) {
    const tbody = document.getElementById('productosBody');
    tbody.innerHTML = '';
    lista.forEach(p => {
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td>${p.code}</td>
            <td>${p.name}</td>
            <td>$${p.priceUnit.toFixed(2)}</td>
            <td>${p.category}</td>
            <td class="actions">
                <button class="btn btn-info btn-sm" onclick="detalleProducto(${p.code})">Detalle</button>
                <button class="btn btn-danger btn-sm" onclick="eliminarProducto(${p.code})">Eliminar</button>
            </td>
        `;
        tbody.appendChild(tr);
    });
    document.getElementById('noResults').style.display = lista.length === 0 ? 'block' : 'none';
}

function filtrarProductos() {
    const filtro = document.getElementById('searchInput').value.toLowerCase();
    const filtrados = productos.filter(p =>
        p.name.toLowerCase().includes(filtro) ||
        p.category.toLowerCase().includes(filtro) ||
        String(p.code).includes(filtro)
    );
    renderizarProductos(filtrados);
}

async function eliminarProducto(code) {
    if (!confirm('¿Eliminar el producto con código ' + code + '?')) return;
    const errorMsg = document.getElementById('errorMsg');
    try {
        errorMsg.style.display = 'none';
        const res = await fetch(`http://localhost:8082/api/v1/products/${code}`, { method: 'DELETE' });
        if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
        productos = productos.filter(p => p.code !== code);
        renderizarProductos(productos);
    } catch (err) {
        errorMsg.textContent = 'Error al eliminar: ' + err.message;
        errorMsg.style.display = '';
    }
}

async function detalleProducto(code) {
    const errorMsg = document.getElementById('errorMsg');
    try {
        errorMsg.style.display = 'none';
        const res = await fetch(`http://localhost:8082/api/v1/products/${code}`);
        if (!res.ok) throw new Error(`Error ${res.status}: ${res.statusText}`);
        const p = await res.json();
        document.getElementById('detCode').textContent = p.code;
        document.getElementById('detName').textContent = p.name;
        document.getElementById('detPrice').textContent = '$' + p.priceUnit.toFixed(2);
        document.getElementById('detCategory').textContent = p.category;
        document.getElementById('modalOverlay').classList.add('active');
    } catch (err) {
        errorMsg.textContent = 'Error al obtener detalle: ' + err.message;
        errorMsg.style.display = '';
    }
}

function cerrarModal() {
    document.getElementById('modalOverlay').classList.remove('active');
}

cargarProductos();

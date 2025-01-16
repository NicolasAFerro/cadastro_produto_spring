$("#inputPrice").mask("000.000.000.000.000,00", { reverse: true });
var products = [];

var categories = [];

//chamada no carregamento do documento
loadCategories();
loadProducts();

//chamadas assincronas. tem que bloquear
function loadCategories() {
  //com o podemos fazer sincronas ou assincronas
  $.ajax({
    url: "http://localhost:8080/categories",
    type: "GET",
    async: false,
    success: (response) => {
      categories = response;
      for(var cat of categories){ 
        document.getElementById('selectCategory').innerHTML+=`<option value=${cat.id}>${cat.name}</option>`
      }
    },
  });
}

function loadProducts() {
  //getJson só chamada assincrona
  $.getJSON("http://localhost:8080/products", (response) => {
    products = response;
    for (let prod of products) {
      addNewRow(prod);
    }
  });
}

function addNewRow(prod) {
  var table = document.getElementById("productsTable");

  var newRow = table.insertRow();

  var formatter = new Intl.NumberFormat("pt-br", {
    style: "currency",
    currency: "BRL",
  });

  var idNode = document.createTextNode(prod.id);
  var nameNode = document.createTextNode(prod.name);

  var priceNode = document.createTextNode(formatter.format(prod.price));

  var categoria = categories[prod.idCategory - 1].name;
  var categoryNode = document.createTextNode(categoria);

  var options = "";
  if (prod.newProduct) {
    options =
      '<span class="badge text-bg-primary d-none d-md-table-cell me-3">L</span>';
  }
  if (prod.promotion) {
    options +=
      '<span class="badge text-bg-success d-none d-md-table-cell">P</span';
  }

  newRow.insertCell().appendChild(idNode);
  newRow.insertCell().appendChild(nameNode);
  newRow.insertCell().appendChild(priceNode);
  newRow.insertCell().appendChild(categoryNode);
  var descriptionNode = document.createTextNode(prod.description);
  var cell = newRow.insertCell();
  cell.className = "d-none d-md-table-cell";
  cell.appendChild(descriptionNode);

  newRow.insertCell().innerHTML = options;
}
function save() {
  try {
    var prod = {
      name: document.getElementById("inputName").value,
      description: document.getElementById("inputDescription").value,
      price: document
        .getElementById("inputPrice")
        .value.replace(/\./g, "")
        .replace(",", "."),
      idCategory: document.getElementById("selectCategory").value,
      promotion: document.getElementById("checkBoxPromotion").checked,
      newProduct: document.getElementById("checkBoxLancamento").checked,
    }; 
    $.ajax({
      url: "http://localhost:8080/products",
      type: "POST",
      contentType:"application/json",
      data:JSON.stringify(prod),
      success: (product) => {
        addNewRow(product);
        products.push(product); 
        document.getElementById("formProducts").reset();
      },
    });





   
    
  } catch (exeception) {
    console.log(exeception);
  }
}

var app = new function() {

  this.el = document.getElementById('planes');
  this.url = 'https://my-flightradar-func-app.azurewebsites.net/api/planes';

  this.planes = [];

  // COUNT ALL ITEMS

  this.Count = function(data) {
    var el   = document.getElementById('counter');
    var name = 'planes';

    if (data) {
      if (data == 1) {
        name = 'plane';
      }
      el.innerHTML = data + ' ' + name;

    } else {
      el.innerHTML = 'No ' + name;

    }
  };

  // READ ALL ITEMS

  this.FetchAll = async function() {
    var data = '';
    
    const res = await fetch(this.url);
    const res_data = await res.json();
    console.log(res_data);
    this.planes = res_data;

    if (this.planes.length > 0) {
      for (i = 0; i < this.planes.length; i++) {
        data += `<div class="col-md-4">
        <div id="${this.planes[i].id}" class="card mb-4 box-shadow item-card text-center">
          <div class="card-body" style="margin: auto;">
            <img src="img/placeholder-200x200.jpg" />
            <h5 class="card-title"><strong>${this.planes[i].reg_number}</strong></h5>
            <p class="card-text">${this.planes[i].company} | ${this.planes[i].model}<br>
              Speed (mph): ${this.planes[i].speed_in_mph}<br>
              Departure: ${this.planes[i].departure_airport}, ${this.planes[i].scheduled_departure}<br>
              Arrival: ${this.planes[i].arrival_airport}, ${this.planes[i].scheduled_arrival}<br></p>
              <p class="card-text" style="text-align: justify;">Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed eleifend cursus
              nibh, dignissim interdum tortor fermentum nec.</p>
          </div>
        </div>
        </div>`;
      }
    }

    this.Count(this.planes.length);

    return this.el.innerHTML = data;
  };

  // CREATE ITEM

  this.Add = async function () {
    speed_in_mph =          Number(document.getElementById('add-speed-in-mph').value);
    company =               document.getElementById('add-company').value;
    model =                 document.getElementById('add-model').value;
    reg_number =            document.getElementById('add-reg-number').value;
    departure_airport =     document.getElementById('add-departure-airport').value;
    arrival_airport =       document.getElementById('add-arrival-airport').value;
    scheduled_departure =   document.getElementById('add-scheduled-departure').value;
    scheduled_arrival =     document.getElementById('add-scheduled-arrival').value;

    var new_object = {
      speed_in_mph:         speed_in_mph,
      company:              company,
      model:                model,
      reg_number:           reg_number,
      departure_airport:    departure_airport,
      arrival_airport:      arrival_airport,
      scheduled_departure:  scheduled_departure,
      scheduled_arrival:    scheduled_arrival
    };

    console.log(new_object);

    await fetch(this.url, {
      method: 'POST',
      mode: 'cors',
      headers: {
        'Content-Type': 'application/json;charset=utf-8'
      },
      body: JSON.stringify(new_object)
    })
    .then(() => {
      window.location.href = "./index.html";
    })
    .catch((error) => console.log(error));

    speed_in_mph = '';
    company = '';
    model = '';
    reg_number = '';
    departure_airport = '';
    arrival_airport = '';
    scheduled_departure = '';
    scheduled_arrival = '';

    this.FetchAll();
  };

  // DISPLAY ADD FORM

  this.DisplayAddForm = function() {
    if (document.getElementById('add-form').style.display === 'none') {
      document.getElementById('add-form').style.display = 'block';
    } else {
      document.getElementById('add-form').style.display = 'none';
    }
  };

  // ADD FORM DATA VALIDATION

  document.getElementById('add-form').addEventListener('invalid', (function () {
    return function (e) {
      e.preventDefault();
      var modal = document.getElementById('add-modal');
      var span = document.getElementsByClassName('close')[0];
      
      modal.style.display = 'block';
      
      span.onclick = function() {
        modal.style.display = 'none';
      }

      window.onclick = function(event) {
        if (event.target == modal) {
          modal.style.display = 'none';
        }
      }
    };
  })(), true);

  this.Refresh = function() {
    this.FetchAll();
    this.DisplayAddForm();
  };

};

app.Refresh();

var app = new function() {

  this.el = document.getElementById('data_blocks');
  this.url = 'http://20.81.83.53/api/get-data';

  this.data_blocks = [];

  // COUNT ALL ITEMS

  this.Count = function(data) {
    var el       = document.getElementById('counter');
    el.innerHTML = (data == 0 ? 'No' : data) + ' data block' + (data == 1 ? '' : 's');
  };

  // READ ALL ITEMS

  this.FetchAll = async function() {
    var data = '';
    
    const res = await fetch(this.url);
    const res_data = await res.json();
    console.log(res_data);
    this.data_blocks = res_data;

    if (this.data_blocks.length > 0) {
      for (i = 0; i < this.data_blocks.length; i++) {
        data += `<div class="col-md-4">
        <div id="${this.data_blocks[i].id}" class="card mb-4 box-shadow item-card text-center">
          <div class="card-body" style="margin: auto;">
            <h5 class="card-title"><strong>${this.data_blocks[i].id} / ${this.data_blocks[i].device_id} (${this.data_blocks[i].protocol})</strong></h5>
            <p class="card-text">${this.data_blocks[i].measure_time} | ${this.data_blocks[i].value}<br>
          </div>
        </div>
        </div>`;
      }
    }

    this.Count(this.data_blocks.length);

    return this.el.innerHTML = data;
  };

  this.Refresh = function() {
    this.FetchAll();
  };

};

app.Refresh();

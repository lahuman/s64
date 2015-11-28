var User = function(id) {
    Object.defineProperty(this, 'id', {
        value: id
    }, 
    dispatch = function(ev){
		var i = this.listener.length;
		while(i--) this.listener[i].call(this, ev); 
	});
    this.listener = [];
    this.que = [];
    
};
Object.defineProperties(User.prototype, {
    'addListener': {
        value: function(v) {
            if (this.listener.indexOf(v) == -1) this.listener.push(v);
        }
    },
    'create': {
        value: function(v) {
            this.que.push('create', v);
        }
    },
    'delete': {
        value: function() {
            this.que.push('delete', null);
        }
    },
    'select': {
        value: function() {
            this.que.push('select', null);
        }
    },
    'update': {
        value: function(v) {
            this.que.push('update', v);
        }
    },
    'flush': {
        value: function() {
        	console.log(this.que.length);
            var iter, que = this.que.slice(0),
                i = 0,
                j = que.length,
                self = this;
            this.que.length = 0;
            iter = function() {
                var command, data;
                do{
                	console.log(i);
                	
	                if (i < j) {
	                    command = que[i++];
	                    data = que[i++];
	                    //ajax.load(url[command] + self.id, iter, data);
	                }	   
	                dispatch.call(self, command);
                }while(i < j);
                dispatch.call(self, 'flushed');
            };
            iter();
        }
    }
});

var hika = new User('hika');
hika.addListener(function(ev) {
    console.log(ev);
});
hika.create({
    age: 99,
    email: 'hika@xx.xx'
});
hika.update({
    age: 100
});
hika.flush();

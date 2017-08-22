/*
---
description:     
  - MultiSelect is a MooTools plugin that turns your checkbox set into one single multi-select dropdown menu. MultiSelect is also completely CSS skinnable.

authors:
  - BlaÅ¾ MaleÅ¾iÄ� (http://twitter.com/blazmalezic)

version:
  - 1.3.1

license:
  - MIT-style license

requires:
  core/1.2.1:   '*'

provides:
  - MultiSelect
...
*/
var MultiSelect = new Class({
	
	Implements: [Options], 
	
	options: {
		boxes: 'input[type=checkbox]', 	// checkbox selector
		groups: 'input[type=group]', 	// checkbox selector
		labels: 'label', 				// label selector
		monitorText: ' selected',		// monitor text (localization)
		allSelectedText: 'All',
		containerClass: 'MultiSelect1', 	// element container CSS class
		monitorClass: 'monitor',		// monitor CSS class
		monitorActiveClass: 'active',	// monitor open CSS class
		itemSelectedClass: 'selected',	// list item selected CSS class
		itemHoverClass: 'hover',			// list item hover CSS class - usually we would use CSS :hover pseudo class, but we need this for keyboard navigation functionality
		disabledClass: 'ux-util-hidden'
	}, 
	
	initialize: function(selector, options) {
		// set options
		this.setOptions(options);
		
		// set global action variables
		this.active = false;
		this.action = 'open';
		this.state  = 'closed';
		
		// get elements array
		this.elements = document.getElements(selector);
		
		// off we go...
		this.elements.each(function(element) {
			this.buildMenu(element);
		}, this);
	},
	
	buildMenu: function(element) {
		// create closure
		var self = this;
		
		// add container class (for styling)
		element.addClass(self.options.containerClass);
		
		// create item instances
		var groups = element.getElements(self.options.groups);
		var boxes = element.getElements(self.options.boxes);
		var labels = element.getElements(self.options.labels);
		
		// list container
		var list = new Element('ul', {
			'styles': { display: 'none' },
			'events': {
				'mouseenter': function() { self.action = 'open'; }, 
				'mouseleave': function() { 
					self.action = 'close';
					self.itemHover(this, 'none');
				},
				'touchend': function(e) { e.stopPropagation();}, 
				'click': function(e) { e.stopPropagation();},
							
				'mousedown': function(e) { e.stop(); }, // stop text selection
				'selectstart': function() { return false; }, // stop IE text selection
				
				'keydown': function(e) {
					if (e.key == 'esc') {
						self.toggleMenu('close', monitor, this);
					}
					else if (e.key == 'down' || e.key == 'up') {
						self.itemHover(this, e.key);
					}
				}
			}
		});
		
		var allspanForLinks = new Element('span', {
			'html': 'Select: <div style="float:right; maring-top:0px; font-size:14px;"><a href="#"><input type="button" value="Done"  class="ux-btn -default-action ux-margin-top-1t ux-margin-right-1t " ></a></div>'}
			);
		
		var allLink = new Element('a',{
			'html': 'All',
			'styles' : { margin: '5px' },
			'events': {
				'mousedown': function(e) { 
						if (e.rightClick == false){
							boxes.each(function(box, i) {
								if (!box.hasClass(self.options.disabledClass)){
									box.set('checked', false);
									box.parentElement.removeClass(self.options.itemSelectedClass);
									self.changeItemState(box.parentElement, box, monitor);
								}
							});
							monitor.set('html', '<div><div>' + self.options.allSelectedText + '</div></div>');
						}
					}, // stop text selection
			}
		});
		
		var noneLink = new Element('a',{
			'html': 'None',
			'styles' : { margin: '5px' },
			'events': {
				'mousedown': function(e) { 
						if (e.rightClick == false){
							boxes.each(function(box, i) {
								if (!box.hasClass(self.options.disabledClass)){
									box.set('checked', true);
									box.parentElement.addClass(self.options.itemSelectedClass);
									self.changeItemState(box.parentElement, box, monitor);
								}
							});
						}
					}, // stop text selection
			}
		});
		
		allspanForLinks.adopt(allLink).appendText('|').adopt(noneLink).inject(list);
		
		// list items
		boxes.each(function(box, i) {
			box.addEvents({
				'click': function(e) {
					e.stop();
				},
				'keydown': function(e) {
					if (e.key == 'space') {
						self.active = true;
						self.changeItemState(this.getParent(), this, monitor);
					}
					if (self.active && (e.key == 'down' || e.key == 'up')) {
						self.changeItemState(this.getParent(), this, monitor);
					}
				},
				'keyup': function(e) {
					if (e.key == 'space') {
						self.active = false;
					}
				}
			});
			var label = labels[i];
			if (box.hasClass(self.options.disabledClass)){
				new Element('li').adopt([box, label]).inject(list);
			} else{
				new Element('li', {
					'class': box.get('checked') ? self.options.itemSelectedClass : '',
					'events': {
						'mouseenter': function() {
							if (self.active === true) {
								self.changeItemState(this, box, monitor);
							}
							self.itemHover(list, this);
						},
						'mousedown': function() {
							self.active = true;
							self.changeItemState(this, box, monitor);
						}
					}
				}).adopt([box, label]).inject(list);
			}
			
		});
		// list monitor
		var monitor = new Element('div', {
			'class': self.options.monitorClass,
			'title': self.getHoverTitle(list),
			'html': '<div><div>' + self.changeMonitorValue(list) + '</div></div>',
			'tabindex': 0,
			'events': {
				'mouseenter': function() { self.action = 'open'; }, 
				'mouseleave': function() { self.action = 'close'; },
										
				'click': function() { 
					
					if (this.hasClass(self.options.monitorActiveClass)) {
						self.toggleMenu('close', monitor, list);
					}
					else {
						self.toggleMenu('open', monitor, list);
					}
				},
				'keydown': function(e) {
					if (e.key == 'space' || e.key == 'down' || e.key == 'up') {
						self.action = 'close';
						self.toggleMenu('open', monitor, list);
					}
				},
				
				'mousedown': function(e) { e.stop(); }, // stop text selection
				'selectstart': function() { return false; } // stop IE text selection
			}
		});
		
		// 'global' events
		document.addEvents({
			'mouseup': function() { self.active = false; },
			'click': function() {
				if (self.action == 'close') {
					self.toggleMenu('close', monitor, list);
				}
			},
			'touchend': function(e) {
				self.toggleMenu('close', monitor, list);
			},
			'keydown': function(e) {
				if (e.key == 'esc') {
					self.toggleMenu('close', monitor, list);
					self.itemHover(list, 'none');
				}
				if (self.state == 'opened' && (e.key == 'down' || e.key == 'up')) {
					e.stop();
				}
			}
		});		
		
		// replace element content
		element.empty().adopt([monitor, list]);
	}, 
	
	append: function(selector) {
		var elements = document.getElements(selector);
		this.elements.combine(elements);
		
		elements.each(function(element) {
			this.buildMenu(element);
		}, this);
	}, 
	
	changeItemState: function(item, checkbox, monitor) {
		if (item.hasClass(this.options.itemSelectedClass)) {
			item.removeClass(this.options.itemSelectedClass);
			checkbox.set('checked', false).focus();
		}
		else {
			item.addClass(this.options.itemSelectedClass);
			checkbox.set('checked', true).focus();
		}
		
		monitor.set('html', '<div><div>' + this.changeMonitorValue(item.getParent()) + '</div></div>');
		monitor.title = this.getHoverTitle(item.getParent());
	}, 
	
	getHoverTitle: function(list) {
	    var labels = $$(this.options.labels);
	    var text = "Select";
	    cnt = list.getElements(this.options.boxes).filter(function(box) {
	        return box.get('checked');
	    }).length;
	    if (cnt>1) {
	    	//separate value in header
	        e = list.getElements(this.options.boxes).filter(function(box) {
	            return box.get('checked');
	        });
	        l = e.getNext();
	        text = l.get('text');
	    }
	    if (cnt == 1) {
	        e = list.getElements(this.options.boxes).filter(function(box) {
	            return box.get('checked');
	        });
	        // have you better idea do get label text?
	        l = e.getNext();
	        text = l.get('text');
	    }
	    return text;		
	},
	
	changeMonitorValue: function(list) {
        var labels = $$(this.options.labels);
        var text = "Select";
        cnt = list.getElements(this.options.boxes).filter(function(box) {
            return box.get('checked');
        }).length;
        totalCnt = list.getElements(this.options.boxes).length;
        if (cnt==totalCnt)
        	return this.options.allSelectedText;
        if (cnt>1) {
             text = list.getElements(this.options.boxes).filter(function(box) {
                return box.get('checked');
            }).length + this.options.monitorText;
        }
        if (cnt == 1) {
            e = list.getElements(this.options.boxes).filter(function(box) {
                return box.get('checked');
            });
            // have you better idea do get label text?
            l = e.getNext();
            text = l.get('text');
        }
        return text;
    },
	
	itemHover: function(list, select) {
		var current = list.getElement('li.'+this.options.itemHoverClass);
		
		switch (select) {
			case 'down':
				if (current && (sibling = current.getNext())) current.removeClass(this.options.itemHoverClass);
				else this.itemHover(list, 'last');
				break;
 			case 'up':
				if (current && (sibling = current.getPrevious())) current.removeClass(this.options.itemHoverClass);
				else this.itemHover(list, 'first');
				break;
			case 'none':
				list.getElements('li.'+this.options.itemHoverClass).removeClass(this.options.itemHoverClass);
				break;
			case 'first':
				var sibling = list.getFirst();
				break;
			case 'last':
				var sibling = list.getLast();
				break;
			default:
				if (current) current.removeClass(this.options.itemHoverClass);
				var sibling = select;
				break;
		}
		
		if (sibling){ 
			sibling.addClass(this.options.itemHoverClass);
			var boxElem = sibling.getElement(this.options.boxes); 
			if (boxElem != null){
				sibling.getElement(this.options.boxes).focus();
			}
		}
	},
	
	toggleMenu: function(toggle, monitor, list) {
		if (toggle == 'open') {
			monitor.addClass(this.options.monitorActiveClass);
			list.setStyle('display', '');
			this.itemHover(list, 'first');
			
			this.state = 'opened';
		}
		else {
			// close all MultiSelect menus
			this.elements.getElement('div.monitor').removeClass(this.options.monitorActiveClass);
			this.elements.getElement('ul').setStyle('display', 'none');

			this.action = 'open';
			this.state = 'closed';
		}
		
		if (list.getScrollSize().y > (list.getStyle('max-height').toInt() ? list.getStyle('max-height').toInt() : list.getStyle('height').toInt()))
			list.setStyle('overflow-y', 'scroll');
	}
});
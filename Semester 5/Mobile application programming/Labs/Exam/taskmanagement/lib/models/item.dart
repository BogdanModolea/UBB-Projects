class Item {
  int? id;
  String name;
  String supplier;
  String details;
  String status;
  int? quantity;
  String type;

  Item({
    this.id,
    required this.name,
    required this.supplier,
    required this.details,
    required this.status,
    required this.quantity,
    required this.type,
  });

  factory Item.fromJson(Map<String, dynamic> json) {
    return Item(
        id: json['id'] as int?,
        name: json['name'] as String,
        supplier: json['supplier'] as String,
        details: json['details'] as String,
        status: json['status'] as String,
        quantity: json['quantity'] as int?,
        type: json['type'] as String
    );
  }

  Map<String, dynamic> toJson() {
    return {
      'id': id,
      'name': name,
      'supplier': supplier,
      'details': details,
      'status': status,
      'quantity': quantity,
      'type': type
    };
  }

  Map<String, dynamic> toJsonWithoutId() {
    return {
      'name': name,
      'supplier': supplier,
      'details': details,
      'status': status,
      'quantity': quantity,
      'type': type
    };
  }

  Item copy({
    int? id,
    String? name,
    String? supplier,
    String? details,
    String? status,
    int? quantity,
    String? type,
  }) {
    return Item(
      id: id ?? this.id,
      name: name ?? this.name,
      supplier: supplier ?? this.supplier,
      details: details ?? this.details,
      status: status ?? this.status,
      quantity: quantity ?? this.quantity,
      type: type ?? this.type,
    );
  }

  @override
  String toString() {
    return 'Supply: $name, supplier: $supplier, details: $details, status: $status, quantity: $quantity, type: $type';
  }
}

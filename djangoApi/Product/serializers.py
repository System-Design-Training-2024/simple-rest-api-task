from rest_framework import serializers
from Product.models import Product


class ProductSerializer(serializers.ModelSerializer):
    class Meta:
        model = Product
        fields = ('id',
                  'name',
                  'description',
                  'price',
                  'created_at',
                  'updated_at',
                  )


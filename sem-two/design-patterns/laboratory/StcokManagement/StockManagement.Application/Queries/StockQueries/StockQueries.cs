using StockManagement.Application.Common;
using StockManagement.Application.Projections;

namespace StockManagement.Application.Queries.StockQueries;

public class GetAllStocks : BaseRequest<GetAllStocksResponse>
{
    
}

public class GetAllStocksResponse
{
    public List<StockProjection> StockModels { get; set; }
}

public class GetStockById : BaseRequest<GetStockByIdResponse>
{
    public Guid Id { get; set; }
}

public class GetStockByIdResponse
{
    public StockProjection Stock { get; set; }
}

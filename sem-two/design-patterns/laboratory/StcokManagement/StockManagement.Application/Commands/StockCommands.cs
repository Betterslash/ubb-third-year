using MediatR;
using StockManagement.Application.Common;
using StockManagement.Application.Models;

namespace StockManagement.Application.Commands;

public class AddStockCommand : BaseRequest<AddStockCommandResponse>
{
    public StockModel Stock { get; set; }
}

public class EditStockCommand : BaseRequest<EditStockCommandResponse>
{
    public StockModel Stock { get; set; }
}

public class DeleteStockCommand : BaseRequest<Unit>
{
    public Guid Id { get; set; }
}

public class EditStockCommandResponse
{
    public Guid Id { get; set; }
}

public class AddStockCommandResponse
{
    public Guid Id { get; set; }
}
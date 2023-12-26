namespace DIYHomeAutomationAPI.Models
{
    public class AuthResponse
    {
        public string UserID { get; set; } = null!;

        public string Token { get; set; } = null!;

        public DateTime Expiration { get; set; }
    }
}
